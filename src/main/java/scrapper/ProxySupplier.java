package scrapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import scrapper.proxy_site_parsers.ProxySiteParser;
import scrapper.proxy_site_parsers.ProxypediaParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProxySupplier implements Runnable {
    private Map<String, String> proxyMap;
    private List<ProxySiteParser> parsers; //list of threads checking proxy sites
    private int totalActiveProxies = 10; //later will be changed to reading from properties
    private int proxyRecheckDelaySec = 60; //later will be changed to reading from properties
    private boolean isPaused = false;
    private static final Logger logger = LogManager.getLogger(ProxySupplier.class.getName());

    public ProxySupplier(Map<String, String> proxyMap) {
        this.proxyMap = proxyMap;
    }

    @Override
    public void run() {
        initParsers(proxyMap);
        List<Thread> parseThreadsList = launchParseThreads();
        while (true){
            if (proxyMap.size()>totalActiveProxies){
                if (!isPaused){
                    pauseAllParsers(parseThreadsList);
                }
                try {
                    Thread.sleep(proxyRecheckDelaySec*1000);
                } catch (InterruptedException ignored) {
                }
            }
            else if (isPaused){
                wakeUpAllParsers(parseThreadsList);
            }
        }
    }

    private void wakeUpAllParsers(List<Thread> parseThreadsList) {
        parseThreadsList.forEach(Object::notify);
        logger.info("Proxy finder threads notified");
    }

    private void pauseAllParsers(List<Thread> parseThreadsList) {
        parseThreadsList.forEach(proxyParserThread->{
            try {
                proxyParserThread.wait();
            } catch (InterruptedException ignored) {
            }
        });
        isPaused=true;
        logger.info("Proxy finder threads paused");
    }

    private List<Thread> launchParseThreads() {
        List<Thread> result = new ArrayList<>();
        parsers.forEach(parser->{
            Thread thread = new Thread(parser);
            result.add(thread);
            thread.run();
        });
        logger.info("Proxy finder threads launched");
        return result;
    }

    private void initParsers(Map<String, String> proxyMap) {
        parsers = new ArrayList<>();
        parsers.add(new ProxypediaParser(proxyMap));
    }
}

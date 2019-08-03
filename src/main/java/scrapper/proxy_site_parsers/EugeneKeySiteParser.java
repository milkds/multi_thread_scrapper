package scrapper.proxy_site_parsers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;

public class EugeneKeySiteParser implements ProxySiteParser {

    private Map<String, String> proxyMap;
    private static final String SITE_LINK = "https://proxy.key.dp.ua/filter/?key=1ba857050fb952f2cf6ae7c55468ba62&worked=1";
    private static final Logger logger = LogManager.getLogger(EugeneKeySiteParser.class.getName());
    @Override
    public void run() {
        Document doc = WebUtils.getProxyPageByJsoup(SITE_LINK);
        if (doc!=null){
            String body = doc.getAllElements().text();
            String[] split = body.split(" ");
            Map<String, String> rawProxyMap = new HashMap<>();
            int validProxiesCount = 0;
            int invalidProxiesCount = 0;
            for (int i = 0; i < split.length ; i++) {
                String proxyLine = split[i];
                String[] proxySplit = proxyLine.split(":");
                if (proxySplit.length==2){
                    if (new ProxyValidator().isValid(proxySplit[0], proxySplit[1])){
                        logger.debug("Proxy is valid: " + proxyLine+ ". Total valid proxies: " + ++validProxiesCount);
                        rawProxyMap.put(proxySplit[0], proxySplit[1]);
                    }
                    else {
                        logger.debug("invalid proxy: " + proxyLine+ ". Total invalid proxies: " + ++invalidProxiesCount);
                    }
                }
            }
            System.exit(1);
        }
    }

    public EugeneKeySiteParser(Map<String, String> proxyMap) {
        this.proxyMap = proxyMap;
    }
}

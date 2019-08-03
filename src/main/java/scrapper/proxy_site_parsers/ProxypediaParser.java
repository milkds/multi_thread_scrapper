package scrapper.proxy_site_parsers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import scrapper.ProxySupplier;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProxypediaParser implements ProxySiteParser  {
    private Map<String, String> proxyMap;
    private static final String SITE_LINK = "https://proxypedia.org/";
    private static final Logger logger = LogManager.getLogger(ProxypediaParser.class.getName());


    public ProxypediaParser(Map<String, String> proxyMap) {
        this.proxyMap = proxyMap;
    }

    @Override
    public void run() {
        Map<String, String> rawProxyMap = new HashMap<>();
        while(true){
            //connecting to site
            Document doc = WebUtils.getProxyPageByJsoup(SITE_LINK);

            //checking if connected successfully
            if (doc == null){
                try {
                    Thread.currentThread().wait();
                } catch (InterruptedException ex) {
                    return;
                }
            }
            //parsing doc
            rawProxyMap = parseDocFile(doc);

            //validating
            rawProxyMap.forEach((k,v)->{
                if (new ProxyValidator().isValid(k,v)){
                    logger.info("valid proxy found " + k + ":" + v);
                    proxyMap.put(k,v);
                }
                else {
                    logger.info("invalid proxy "+ k + ":" + v);
                }
            });

            try {
                Thread.sleep(60*11*1000); //site updates once per 10 min, we will wait for 11
            } catch (InterruptedException ex) {
                return;
            }


        }
    }

    private Map<String, String> parseDocFile(Document doc) {
        Map<String, String> result = new HashMap<>();
        Element mainEl = doc.getElementsByTag("main").first();
        Element proxyContainer = mainEl.getElementsByTag("ul").first();
        Elements proxyElements = proxyContainer.getElementsByTag("li");
        proxyElements.forEach(element -> {
            Elements proxyEl = element.getElementsByTag("a");
            if (proxyEl.size()>0){
                String proxyRaw = proxyEl.first().text();
                String split[] = proxyRaw.split(":");
                if (split.length==2){
                    result.put(split[0], split[1]);
                }
            }
        });

        return result;
    }
}

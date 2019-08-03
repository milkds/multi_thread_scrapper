package scrapper;


import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Launcher {
   private Map<String, String> proxyMap = new ConcurrentHashMap<>(); //k = ip, v = port
   private Set<String> links = ConcurrentHashMap.newKeySet();

   public void launch(){
      launchProxySupplier();
      launchLinkSupplier();
      launchSiteParseThreads();
   }

    private void launchSiteParseThreads() {

    }

    private void launchLinkSupplier() {
       //todo: implement
    }

    private void launchProxySupplier() {
        new Thread(new ProxySupplier(proxyMap)).run();
    }


    public Map<String, String> getProxyMap() {
        return proxyMap;
    }
    public Set<String> getLinks() {
        return links;
    }
}

package scrapper.proxy_site_parsers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HideMyNameParser implements ProxySiteParser {
    private Map<String, String> proxyMap;
    private static final String SITE_LINK = "https://hidemyna.me/en/proxy-list/?maxtime=1000&type=h#list";
    private static final Logger logger = LogManager.getLogger(HideMyNameParser.class.getName());

    public HideMyNameParser(Map<String, String> proxyMap) {
        this.proxyMap = proxyMap;
    }

    @Override
    public void run() {
        while (true) {
            //connecting to site
            WebDriver driver = WebUtils.getProxyPageByDriver(SITE_LINK);

            //checking if connected successfully
            if (driver == null){
                try {
                    Thread.currentThread().wait();
                } catch (InterruptedException ex) {
                    return;
                }
            }
            //browsing
            browseBySite(driver);

        }
    }

    private void browseBySite(WebDriver driver) {
        getProxyTable(driver);
    }

    private void getProxyTable(WebDriver driver) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement table = driver.findElement(By.tagName("body"));
        System.out.println(table.getText());
    }

    private Map<String, String> parseDocument(WebDriver driver) {
        return null;
    }
}

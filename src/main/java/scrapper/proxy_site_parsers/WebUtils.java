package scrapper.proxy_site_parsers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;

public class WebUtils {
    private static final Logger logger = LogManager.getLogger(WebUtils.class.getName());

    public static Document getProxyPageByJsoup(String siteLink) {
        while (true) {
            //connecting to site
            Document doc = null;
            int attempts = 0;
            logger.info("trying to connect");
            while (true) {
                try {
                    doc = Jsoup.connect(siteLink).get();
                    logger.info("connected");
                    break;
                } catch (IOException e) {
                    logger.error(siteLink + " is not available. retrying...");
                    try {
                        Thread.sleep(60 * 1000);
                    } catch (InterruptedException ex) {
                        return null;
                    }
                    attempts++;
                    if (attempts == 3) {
                        logger.error("couldn't open site " + siteLink);
                        break;
                    }
                }
            }
            //checking if connected successfully

            return doc;
        }
    }
    public static WebDriver getProxyPageByDriver(String siteLink){
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        //  options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);
        driver.get(siteLink);

        return driver;
    }
}

package scrapper.proxy_site_parsers;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.*;

public class ProxyValidator {

    private static final String URL_TO_CHECK = "https://www.megazip.ru/";
    private static final Logger logger = LogManager.getLogger(ProxyValidator.class.getName());


    public boolean isValid(String ip, String port){
        final Callable<Boolean> stuffToDo = () -> {
            int portInt = 0;
            try {
                portInt = Integer.parseInt(port);
            } catch (NumberFormatException e) {
                return false;
            }

            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                HttpHost target = new HttpHost("www.megazip.ru", 443, "https");
                //   HttpHost proxy = new HttpHost("82.114.241.138", 8080, "http");
                HttpHost proxy = new HttpHost(ip, portInt, "http");

                RequestConfig config = RequestConfig.custom()
                        .setProxy(proxy)
                        .build();
                HttpHead request = new HttpHead("/");
                request.setConfig(config);

                try (CloseableHttpResponse response = httpclient.execute(target, request)) {
                    return response.getStatusLine().getStatusCode() == 200;
                }
            } catch (IOException ignored) {
            }

            return false;
        };

        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Future future = executor.submit(stuffToDo);
        executor.shutdown(); // This does not cancel the already-scheduled task.
        try {
           return  (Boolean) future.get(5, TimeUnit.SECONDS);
        }
        catch (InterruptedException ie) {
            /* Handle the interruption. Or ignore it. */
        }
        catch (ExecutionException ee) {
            /* Handle the error. Or ignore it. */
        }
        catch (TimeoutException te) {
            /* Handle the timeout. Or ignore it. */
        }
        if (!executor.isTerminated())
            executor.shutdownNow();

        return false;
    }


}

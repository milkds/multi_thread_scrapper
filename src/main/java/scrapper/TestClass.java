package scrapper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import scrapper.proxy_site_parsers.EugeneKeySiteParser;
import scrapper.proxy_site_parsers.HideMyNameParser;
import scrapper.proxy_site_parsers.ProxypediaParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


//live proxy: 82.114.241.138: 8080
public class TestClass {

    public static void testProxyoedia(){
        Map<String, String> testMap = new HashMap<>();
        new ProxypediaParser(testMap).run();
    }
    public static void testHideMyName(){
        Map<String, String> testMap = new HashMap<>();
        new HideMyNameParser(testMap).run();
    }
    public static void testEugene(){
        Map<String, String> testMap = new HashMap<>();
        new EugeneKeySiteParser(testMap).run();
    }
/*
    public static void testProxyValidation(){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet("https://www.megazip.ru/");

            System.out.println("Executing request " + httpget.getRequestLine());

            HttpResponse response = httpclient.execute(httpget);
            String responseBody = EntityUtils.toString(response.getEntity());
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void testProxyValidation2(){
       *//* CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpHost target = new HttpHost("www.megazip.ru", 443, "https");
         //   HttpHost proxy = new HttpHost("82.114.241.138", 8080, "http");
            HttpHost proxy = new HttpHost("109.201.96.171", 31773, "http");

            RequestConfig config = RequestConfig.custom()
                    .setProxy(proxy)
                    .build();
            HttpHead request = new HttpHead("/");
            request.setConfig(config);

            System.out.println("Executing request " + request.getRequestLine() + " to " + target + " via " + proxy);

            CloseableHttpResponse response = httpclient.execute(target, request);
            try {
                System.out.println("----------------------------------------");
                if (response.getStatusLine().getStatusCode()==200){
                    System.out.println(true);
                }
                else {
                    System.out.println(false);
                }
                System.out.println(response.getStatusLine());
              //  System.out.println(EntityUtils.toString(response.getEntity()));
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*//*
    }*/
}

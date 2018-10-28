package com.bernard.common.utils;

import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * @author zhangminpeng on 2016-06-13 14:50
 */
public class HttpsUtil {

    /**
     * 创建 HTTPS 链接客户端,默认信任证书,不跟随重定向
     *
     * @return HTTP 连接
     */
    private static CloseableHttpClient createHttpsClient()
            throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(null, (chain, authType) -> true).build();
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext);
        return HttpClients.custom()
                .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36")
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .build();
    }

    /**
     * 发送 get 请求
     *
     * @param url 请求 url
     *            //* @param headers 请求头数组
     * @return 响应结果字符串
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws KeyManagementException
     * @throws IOException
     */
    public static String get(String url)
            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        CloseableHttpClient httpClient = createHttpsClient();
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(5000).build();
        httpGet.setConfig(requestConfig);
        httpGet.setHeader("Content-type", "application/x-www-form-urlencoded");
        //httpGet.setHeaders(headers);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        String result = entity2String(httpResponse.getEntity());

        // 关闭资源
        httpClient.close();
        httpGet.releaseConnection();
        return result;
    }

    public static String get(String url, Header[] headers)
            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        CloseableHttpClient httpClient = createHttpsClient();
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(5000).build();
        httpGet.setConfig(requestConfig);
        httpGet.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpGet.setHeaders(headers);
        //httpGet.setHeaders(headers);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        String result = entity2String(httpResponse.getEntity());

        // 关闭资源
        httpClient.close();
        httpGet.releaseConnection();
        return result;
    }

    /**
     * 发送 get 请求
     *
     * @param url 请求 url
     * @return 响应结果字符串
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws KeyManagementException
     * @throws IOException
     */
  /*  public static String get(String url)
            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        return get(url, null);
    }*/

    /**
     * 发送 post 请求
     *
     * @param url     请求 url
     * @param headers 请求头
     *                // * @param entity  请求实体
     * @return 响应结果字符串
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws KeyManagementException
     * @throws IOException
     */
    public static String post(String url, Header[] headers, JSONObject postData)
            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        System.out.println(url);
        CloseableHttpClient httpClient = createHttpsClient();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(5000).build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeaders(headers);
        httpPost.setEntity(new StringEntity(postData.toString(), HTTP.UTF_8));

        HttpResponse httpResponse = httpClient.execute(httpPost);
        String result = entity2String(httpResponse.getEntity());

        // 关闭资源
        httpClient.close();
        httpPost.releaseConnection();
        return result;
    }


    public static String post(String url, Header[] headers, ArrayList<BasicNameValuePair> postData)
            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        //System.out.println(url);
        CloseableHttpClient httpClient = createHttpsClient();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(5000).build();
        httpPost.setConfig(requestConfig);
        //httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");
        httpPost.setHeaders(headers);
        HttpEntity entity = new UrlEncodedFormEntity(postData, "UTF-8");
        httpPost.setEntity(entity);

        HttpResponse httpResponse = httpClient.execute(httpPost);
        String result = entity2String(httpResponse.getEntity());

        // 关闭资源
        httpClient.close();
        httpPost.releaseConnection();
        return result;
    }

    /**
     * 发送 post 请求
     *
     * @param url 请求 url
     * @return 响应结果字符串
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws KeyManagementException
     * @throws IOException
     */
  /*  public static String post(String url)
            throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return post(url, null, null);
    }*/

    /**
     * 发送 post 请求
     *
     * @param url     请求 url
     * @param headers 请求头
     * @return 响应结果字符串
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws KeyManagementException
     * @throws IOException
     */
/*    public static String post(String url, Header[] headers)
            throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return post(url, headers, null);
    }*/

    /**
     * 发送 post 请求
     *
     * @param url    请求 url
     * @param entity 请求体
     * @return 响应结果字符串
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws KeyManagementException
     * @throws IOException
     */
  /*  public static String post(String url, HttpEntity entity)
            throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return post(url, null, entity);
    }*/

    /**
     * 将响应实体拼接成字符串返回
     *
     * @param entity 响应实体
     * @return 实体字符串
     */
    private static String entity2String(HttpEntity entity) {
        StringBuilder content = new StringBuilder();
        try (InputStream inputStream = entity.getContent();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            // 读取数据
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }


    public static String getWithAuth(String url, String key, String pass) throws IOException {
        HttpGet request = new HttpGet(url);
        String auth = key + ":" + pass;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(5000).build();
        request.setConfig(requestConfig);

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(request);

        // int statusCode = response.getStatusLine().getStatusCode();
        HttpEntity entity = response.getEntity();
        String result = "";
        if (entity != null) {
            result = EntityUtils.toString(entity);
        }
        client.close();
        request.releaseConnection();

        return result;


    }


    public static String postWithAuth(String url, ArrayList<BasicNameValuePair> postData, String key, String pass)
            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        //System.out.println(url);
        CloseableHttpClient httpClient = createHttpsClient();
        HttpPost httpPost = new HttpPost(url);
        String auth = key + ":" + pass;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(5000).build();
        httpPost.setConfig(requestConfig);
        //httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");
        //httpPost.setHeaders(headers);
        HttpEntity entity = new UrlEncodedFormEntity(postData, "UTF-8");
        httpPost.setEntity(entity);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        String result = entity2String(httpResponse.getEntity());
        // 关闭资源
        httpClient.close();
        httpPost.releaseConnection();
        return result;
    }

}
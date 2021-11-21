package com.wtrue.rical.common.utils;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: meidanlong
 * @date: 2021/11/21 7:29 PM
 */
public class HttpUtil {

    public static String doGet(String url) {							// 无参数get请求
        return doGet(url, null);
    }

    public static String doGet(String url, Map<String, String> param) {	// 带参数get请求
        CloseableHttpClient httpClient = HttpClients.createDefault();	// 创建一个默认可关闭的Httpclient 对象
        String resultMsg = "";											// 设置返回值
        CloseableHttpResponse response = null;							// 定义HttpResponse 对象
        try {
            URIBuilder builder = new URIBuilder(url);					// 创建URI,可以设置host，设置参数等
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            HttpGet httpGet = new HttpGet(uri);					// 创建http GET请求
//            httpGet.setHeader(key,value);                     //设置请求的请求头
            response = httpClient.execute(httpGet);						// 执行请求
            if (response.getStatusLine().getStatusCode() == 200) {		// 判断返回状态为200则给返回值赋值
                resultMsg = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {														// 不要忘记关闭
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultMsg;
    }

    public static String doPost(String url) {							// 无参数post请求
        return doPost(url, null);
    }

    public static String doPost(String url, Map<String, String> param) {// 带参数post请求
        CloseableHttpClient httpClient = HttpClients.createDefault();	// 创建一个默认可关闭的Httpclient 对象

        CloseableHttpResponse response = null;
        String resultMsg = "";
        try {
            HttpPost httpPost = new HttpPost(url);						// 创建Http Post请求
//            httpPost.setHeader(key,value);                            //设置post请求的请求头
            if (param != null) {										// 创建参数列表
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);// 模拟表单
                httpPost.setEntity(entity);
            }
            response = httpClient.execute(httpPost);					// 执行http请求
            if (response.getStatusLine().getStatusCode() == 200) {
                resultMsg = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultMsg;
    }

    public static String doPostJson(String url, String json) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            HttpPost httpPost = new HttpPost(url);
//            httpPost.setHeader(key,value);                            //设置post请求的请求头
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);     //指定传输参数为json
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static void main(String[] args) {
        Map<String, String> m = new HashMap<>();
        m.put("appName", "test");
        String s = HttpUtil.doPost("http://localhost:8001/job/handler/register", m);
        System.out.println(s);
    }

}

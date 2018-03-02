package com.example.Utils;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 工具类
 * @author rzd
 * @date 2018/3/1
 */
public class Utils {
    /**
     * 根据输入流获取网页字符串, 并关闭输入流
     * @param inputStream 输入流
     * @return 网页字符串
     */
    public static String getPage(InputStream inputStream) {
        BufferedReader bf = null;

        try {
            bf = new BufferedReader(new InputStreamReader(inputStream, "gbk"));
        } catch (UnsupportedEncodingException e) {
            logger.error("解码错误");
            logger.error(e);
        }

        String line;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            if (bf != null) {
                while ((line = bf.readLine()) != null) {
                    stringBuilder.append(line).append('\n');
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                logger.error("输入流关闭异常");
                logger.error(e);
            }
        }

        return stringBuilder.toString();

    }

    public static List<javax.servlet.http.Cookie> servletCookiesFromApacheCookies(List<org.apache.http.cookie.Cookie> cookies) {
        List<javax.servlet.http.Cookie> servletCookies = new ArrayList<>();
        for (org.apache.http.cookie.Cookie c :
                cookies) {
            javax.servlet.http.Cookie servletCookie = new javax.servlet.http.Cookie(c.getName(), c.getValue());
            servletCookie.setPath("/data");
            servletCookie.setMaxAge(5 * 60);
            servletCookies.add(servletCookie);
        }
        return servletCookies;
    }

    public static List<java.net.HttpCookie> httpCookiesFromApacheCookies(List<org.apache.http.cookie.Cookie> cookies) {
        List<java.net.HttpCookie> httpCookies = new ArrayList<>();
        for (org.apache.http.cookie.Cookie c :
                cookies) {
            java.net.HttpCookie httpCookie = new java.net.HttpCookie(c.getName(), c.getValue());
            httpCookie.setDomain(c.getDomain());
            httpCookies.add(httpCookie);
        }
        return httpCookies;
    }

    public static List<java.net.HttpCookie> httpCookiesFromServletCookies(List<javax.servlet.http.Cookie> cookies) {
        List<java.net.HttpCookie> httpCookies = new ArrayList<>();
        for (javax.servlet.http.Cookie c :
                cookies) {
            java.net.HttpCookie httpCookie = new java.net.HttpCookie(c.getName(), c.getValue());
            httpCookie.setDomain(c.getDomain());
            httpCookies.add(httpCookie);
        }
        return httpCookies;
    }

    public static InputStream httpGet(List<java.net.HttpCookie> cookies, String getURL) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(getURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("cookie", netHeaderString(cookies));
            connection.connect();
            return connection.getInputStream();
        } catch (IOException e) {
            // Log
            e.printStackTrace();
            return null;
        }
    }

    private static String netHeaderString(List<java.net.HttpCookie> cookies) {
        StringBuilder builder = new StringBuilder();
        for (java.net.HttpCookie c :
                cookies) {
            builder.append(c.getName()).append("=").append(c.getValue()).append(";");
        }
        return builder.toString();
    }

    private static final Log logger = LogFactory.getLog(Utils.class);

}

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
            getLogger().error("解码错误");
            getLogger().error(e);
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
                getLogger().error("输入流关闭异常");
                getLogger().error(e);
            }
        }

        return stringBuilder.toString();

    }

    private static synchronized Log getLogger() {
        return logger;
    }

    private static final Log logger = LogFactory.getLog(Utils.class);

}

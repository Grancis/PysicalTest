package com.example.spiders;

import com.example.domain.Data;
import com.example.Utils.Utils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 体育学院的爬虫, 所有和体侧相关的操作都由这个类完成
 *
 * @author rzd
 * @date 2018/3/1
 */
public class Spider {
    /**
     * 登录体育学院, 返回httpClient保存登录状态
     * @param xh 学号
     * @param xm 密码
     * @return 包含登录状态的httpClient
     * @throws IOException 爬虫运行时抛出的异常
     */
    public static CloseableHttpClient httpClientAfterLogin(String xh, String xm) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            // 必须按下面的顺序登录

            // 先访问主页
            httpClient.execute(new HttpGet(HOST_URL)).close();
            // 然后访问跳转页
            httpClient.execute(new HttpGet(MID_URL)).close();
            // 最后登录
            List<NameValuePair> postParams = new ArrayList<>();
            postParams.add(new BasicNameValuePair("xh", xh));
            postParams.add(new BasicNameValuePair("xm", xm));
            HttpPost httpPost = new HttpPost(LOGIN_URL);
            httpPost.setEntity(new UrlEncodedFormEntity(postParams));
            httpClient.execute(httpPost).close();
        } catch (IOException e) {
            getLogger().error("登录错误");
            getLogger().error(e);
            throw e;
        }
        return httpClient;
    }

    /**
     * 登出体育学院, 释放httpClient
     * @param httpClient 包含登录状态的httpClient
     * @throws IOException 爬虫运行时抛出的异常
     */
    public static void logOut(CloseableHttpClient httpClient) throws IOException {
        try {
            httpClient.execute(new HttpGet(LOGOUT_URL)).close();
        } catch (IOException e) {
            getLogger().error("登出错误!");
            getLogger().error(e);
            throw e;
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                getLogger().error("释放httpClient错误");
                getLogger().error(e);
            }
        }
    }

    /**
     * 访问并解析体侧数据页面
     *
     * @param httpClient 包含登录状态的httpClient
     * @return Data的List, 每一项是一次体侧的数据
     * @throws IOException 爬虫运行时抛出的异常
     */
    public static List<Data> getData(CloseableHttpClient httpClient) throws IOException {
        // 获取页面
        String page;
        try {
            page = Utils.getPage(httpClient.execute(new HttpGet(DATA_URL)).getEntity().getContent());
        } catch (IOException e) {
            getLogger().error("获取数据失败");
            getLogger().error(e);
            throw e;
        }
        Document doc = Jsoup.parse(page);
        Elements elements = doc.select("tbody tr:gt(4)");

        assert (elements.size() % 2 == 0);

        // 解析页面
        List<Data> dataList = new ArrayList<>();
        // 体侧数据和体侧分数在两行相邻的tr中, 所以index每次增加2
        for (int trIndex = 0; trIndex < elements.size() - 1; trIndex += 2) {
            Data data = new Data();
            parseName(doc, data);
            parseDataRow(elements.get(trIndex), data);
            parseScoreRow(elements.get(trIndex + 1), data);
            dataList.add(data);
        }
        return dataList;
    }

    private static void parseName(Document doc, Data data) {
        data.setName(doc.select("table caption").text().trim());
    }

    /**
     * 解析包含体侧数据的tr
     * @param data 自定义的类用来保存体侧数据
     * @param element 包含体侧数据的tr
     */
    private static void parseDataRow(Element element, Data data) {
        Elements tds = element.select("tr td:gt(0)").select(":lt(14)");
        data.setTerm(element.select("tr td:eq(15)").text().trim());
        for (Element e :
                tds) {
            data.getDataList().add(e.select("div").text().trim());
        }
    }

    /**
     * 解析包含体侧分数的tr
     * @param element 包含体侧分数的tr
     * @param data 自定义的类用来保存体侧数据
     */
    private static void parseScoreRow(Element element, Data data) {
        Elements tds = element.select("tr td:gt(0)").select(":lt(13)");
        for (Element e :
                tds) {
            // 体侧分数在td中的div子元素中
            data.getScores().add(e.select("div").text().trim());
        }
        // 由于视力右这一项没有div子元素且为空, 为了代码的流畅性
        // 没有选择增加分支判断而选择直接添加一个空字符串
        data.getScores().add("");

        data.setAssessment(element.select("tr td:eq(14)").text().trim());
        data.setSuggestion(element.select("tr td:eq(15)").text().trim());
    }

    private static synchronized Log getLogger() {
        return logger;
    }

    private static final String LOGIN_URL = "http://pead.scu.edu.cn/jncx/logins.asp";
    private static final String MID_URL = "http://pead.scu.edu.cn/jncx/?security_verify_data=313932302c31303830";
    private static final String DATA_URL = "http://pead.scu.edu.cn/jncx/tcsh2.asp";
    private static final String LOGOUT_URL = "http://pead.scu.edu.cn/jncx/out.asp";
    private static final String HOST_URL = "http://pead.scu.edu.cn/jncx/";
    private static final Log logger = LogFactory.getLog(Spider.class);
}

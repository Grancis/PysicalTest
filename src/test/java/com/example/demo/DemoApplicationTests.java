package com.example.demo;

import com.example.spiders.Spider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void spiderTest() {
        try {
            CloseableHttpClient httpClient = Spider.httpClientAfterLogin("2016141462308", "462308");
            System.out.println(Spider.getData(httpClient));
            Spider.logOut(httpClient);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

package com.example.services;

import com.example.domain.Data;
import com.example.spiders.Spider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * 获取体侧数据的业务逻辑
 *
 * @author rzd
 * @date 2018/3/1
 */
@Service
public class GetDataService {
    /**
     * 运行爬虫获取体侧数据
     * @param httpClient 从session中取出的httpClient
     * @return 体侧数据
     * @throws IOException 爬虫运行时抛出的异常
     */
    public List<Data> getData(CloseableHttpClient httpClient) throws IOException {
        assert (httpClient != null);

        return Spider.getData(httpClient);
    }
}

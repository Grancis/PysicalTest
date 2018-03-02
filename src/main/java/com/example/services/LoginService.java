package com.example.services;

import com.example.spiders.Spider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 登录登出的业务逻辑
 * @author rzd
 * @date 2018/3/1
 */
@Service
public class LoginService {
    public CloseableHttpClient httpClientAfterLogin(String xh, String xm) throws IOException {
        return Spider.httpClientAfterLogin(xh, xm);
    }

    public void logOut(CloseableHttpClient httpClient) throws IOException {
        Spider.logOut(httpClient);
    }
}

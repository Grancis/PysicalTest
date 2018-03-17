package com.example.actions;

import com.example.domain.Data;
import com.example.services.GetDataService;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * 获取体侧数据action
 *
 * @author rzd
 * @date 2018/3/1
 */
@RestController
public class GetTestDataController {
    @Autowired
    public GetTestDataController(GetDataService getDataService) {
        this.getDataService = getDataService;
    }

    private GetDataService getDataService;

    @RequestMapping("/data")
    public List<Data> getData(HttpSession session) {
        CloseableHttpClient httpClient = (CloseableHttpClient) session.getAttribute("client");
        try {
            return getDataService.getData(httpClient);
        } catch (IOException e) {
            return null;
        }
    }
}

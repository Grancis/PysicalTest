package com.example.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 保存体侧数据的类
 *
 * @author rzd
 * @date 2018/3/2
 */
public class Data {
    private String term;
    private String name;
    private String assessment;
    private List<String> dataList;
    private List<String> scores;
    private String suggestion;

    public Data() {
        dataList = new ArrayList<>();
        scores = new ArrayList<>();
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public List<String> getDataList() {
        return dataList;
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
    }

    public List<String> getScores() {
        return scores;
    }

    public void setScores(List<String> scores) {
        this.scores = scores;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    @Override
    public String toString() {
        return name + '\n'
                + term + '\n'
                + dataList + '\n'
                + scores + '\n'
                + assessment + '\n'
                + suggestion;
    }
}

package com.devlogs.osg.apolo.fptsurvey.domain.data.model;

import java.util.List;

public class QuickQuestionNetworkModel {
    private String id;
    private String question;
    private List<String> answer;
    private List userAnswer;


    public QuickQuestionNetworkModel (String id, String question, List<String> answer, List<List> userAnswer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.userAnswer = userAnswer;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

    public void setUserAnswer(List<List> userAnswer) {
        this.userAnswer = userAnswer;
    }

    public List<List<String>> getUserAnswer() {
        return userAnswer;
    }

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswer() {
        return answer;
    }
}

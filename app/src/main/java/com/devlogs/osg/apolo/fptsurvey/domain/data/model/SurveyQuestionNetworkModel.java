package com.devlogs.osg.apolo.fptsurvey.domain.data.model;

public class SurveyQuestionNetworkModel {
    private String id;
    private String question;
    private String[] answers;

    public SurveyQuestionNetworkModel(String id, String question, String[] answers) {
        this.id = id;
        this.question = question;
        this.answers = answers;
    }

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }
}

package com.devlogs.osg.apolo.fptsurvey.domain.data.model;

public class SurveyResultNetworkModel {
    private String id;
    private String ownerEmail;
    private String ownerName;
    private String surveyId;
    private int[] answer;

    public SurveyResultNetworkModel(String id, String ownerEmail, String ownerName, String surveyId, int[] answer) {
        this.id = id;
        this.ownerEmail = ownerEmail;
        this.ownerName = ownerName;
        this.surveyId = surveyId;
        this.answer = answer;
    }

    public String getId() {
        return id;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public int[] getAnswer() {
        return answer;
    }
}

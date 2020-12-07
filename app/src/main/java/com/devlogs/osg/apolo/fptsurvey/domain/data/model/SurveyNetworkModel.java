package com.devlogs.osg.apolo.fptsurvey.domain.data.model;

public class SurveyNetworkModel {
    private String id;
    private String name;
    private String description;
    private int numberOfQuestion;
    private String topicId;

    public SurveyNetworkModel(String id, String name, String description, int numberOfQuestion, String topicId) {
        this.id = id;
        this.name = name;
        this.numberOfQuestion = numberOfQuestion;
        this.description = description;
        this.topicId = topicId;
    }

    public String getTopicId() {
        return topicId;
    }

    public int getNumberOfQuestion() {
        return numberOfQuestion;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

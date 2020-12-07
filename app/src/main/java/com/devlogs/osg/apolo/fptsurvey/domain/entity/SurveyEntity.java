package com.devlogs.osg.apolo.fptsurvey.domain.entity;
public class SurveyEntity {
    private String id;
    private String title;
    private String description;
    private int numberOfQuestion;
    private String topicId;

    public SurveyEntity(String id, String title, String description, int numberOfQuestion, String topicId) {
        this.id = id;
        this.title= title;
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

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}

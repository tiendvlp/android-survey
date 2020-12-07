package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel;

import android.graphics.drawable.ColorDrawable;

import java.io.Serializable;

public class SurveyPM implements Serializable {
    private String title;
    private String id;
    private int numOfQuestion;
    private int backgroundColor;
    private String description;
    private String topicId;

    public SurveyPM(String title, int numOfQuestion, String description, String topicId, String id) {
        this.title = title;
        this.topicId = topicId;
        this.numOfQuestion = numOfQuestion;
        this.id = id;
        this.description = description;
    }


    public String getId() {
        return id;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public String getTopicId() {
        return topicId;
    }

    public String getTitle() {
        return title;
    }

    public int getNumOfQuestion() {
        return numOfQuestion;
    }

    public String getDescription() {
        return description;
    }
}

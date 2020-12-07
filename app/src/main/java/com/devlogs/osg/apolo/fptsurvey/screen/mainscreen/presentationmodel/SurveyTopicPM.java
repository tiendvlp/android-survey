package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel;

import java.io.Serializable;

public class SurveyTopicPM implements Serializable {
    private String id;
    private String title;
    private String progress;

    public SurveyTopicPM(String title, String progress, String id) {
        this.title = title;
        this.progress = progress;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getProgress() {
        return progress;
    }
}

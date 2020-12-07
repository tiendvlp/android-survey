package com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.presentationmodel;

import java.io.Serializable;
import java.util.List;

public class SurveyPM implements Serializable {
    private String surveyName;
    private String surveyId;
    private int numberOfCheckedAnswer = 0;

    public SurveyPM(String surveyId, String surveyName) {
        this.surveyName = surveyName;
        this.surveyId = surveyId;
    }

    public int getNumberOfCheckedAnswer() {
        return numberOfCheckedAnswer;
    }

    public void setNumberOfCheckedAnswer(int numberOfCheckedAnswer) {
        this.numberOfCheckedAnswer = numberOfCheckedAnswer;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public String getSurveyName() {
        return surveyName;
    }

}

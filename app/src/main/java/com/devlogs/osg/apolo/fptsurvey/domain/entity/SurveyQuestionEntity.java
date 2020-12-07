package com.devlogs.osg.apolo.fptsurvey.domain.entity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SurveyQuestionEntity {
    private String id;
    private String question;
    private String[] answers;

    public SurveyQuestionEntity(String id, String question, String[] answers) {
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


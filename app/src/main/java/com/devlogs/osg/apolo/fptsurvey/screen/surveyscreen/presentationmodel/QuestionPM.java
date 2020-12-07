package com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.presentationmodel;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class QuestionPM implements Serializable {
    private String questionContent;
    private String[] answers;
    private int checkedAnswer = -1;

    public QuestionPM(@NonNull String questionContent, @NonNull String[] answers) {
        this.questionContent = questionContent;
        this.answers = answers;
    }

    public int getCheckedAnswer() {
        return checkedAnswer;
    }

    public void setCheckedAnswer(int checkedAnswer) {
        this.checkedAnswer = checkedAnswer;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public String[] getAnswers() {
        return answers;
    }
}

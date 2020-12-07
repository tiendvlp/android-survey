package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel;

import java.util.List;

public class QuickQuestionPM {
    public static class QuickQuestionAnswersOutOfBoundsException extends RuntimeException {
        public QuickQuestionAnswersOutOfBoundsException () {
            super("Only accept maximum 3 answers");
        }
    }

    private String question;
    private List<String> answers;
    private String questionId;

    public QuickQuestionPM(String questionId, String question, List<String> answers)   {
        this.questionId = questionId;
        this.question = question;
        this.answers = answers;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

}

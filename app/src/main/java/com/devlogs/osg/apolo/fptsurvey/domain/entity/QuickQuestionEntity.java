package com.devlogs.osg.apolo.fptsurvey.domain.entity;

import java.util.List;

public class QuickQuestionEntity {
    private String id;
    private String question;
    private List<String> answer;
    private List userAnswer;

    public class QuickQuestionAnswerOutOfBound extends RuntimeException {
        public QuickQuestionAnswerOutOfBound () {
            super("Your answer in quick question can not more than 3");
        }
    }

    public QuickQuestionEntity (String id, String question, List<String> answer, List<List<String>> userAnswer) {
        if (answer.size() > 3) {throw new QuickQuestionAnswerOutOfBound();}
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.userAnswer = userAnswer;
    }

    public List<List<String>> getUserAnswer() {
        return userAnswer;
    }

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswer() {
        return answer;
    }
}

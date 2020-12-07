package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.controller;

import com.devlogs.osg.apolo.fptsurvey.quickquestion.PushQuickQuestionResultUseCase;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;

class PushQuickQuestionResultController {
    private PushQuickQuestionResultUseCase mUseCase;

    @Inject
    public PushQuickQuestionResultController (PushQuickQuestionResultUseCase push) {
        mUseCase = push;
    }

    public Completable push (String question, int answer, String email) {
        return mUseCase.PushQuickQuestionResultUseCase(question, answer, email);
    }
}

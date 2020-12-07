package com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.controller;

import android.util.Log;

import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.screenstate.MainScreenPresentationState;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.screenstate.SurveyScreenPresentationState;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.screenstate.SurveyScreenPresentationStateStore;
import com.devlogs.osg.apolo.fptsurvey.surveyresult.PublishResultUseCaseSync;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

class SubmitAnswerController {
    private SurveyScreenPresentationStateStore mStateStore;
    private PublishResultUseCaseSync mUseCase;

    public static class SubmitAnswerError extends RuntimeException {

        public SubmitAnswerError () {
            super("Can not upload your result");
        }
    }

    @Inject
    public SubmitAnswerController (SurveyScreenPresentationStateStore stateStore, PublishResultUseCaseSync publishResultUseCaseSync) {
        mStateStore =stateStore;
        mUseCase = publishResultUseCaseSync;
    }

    public void submitAnswer (String surveyId, List<Integer> answer) {
        mUseCase.execute(surveyId, answer).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                    mStateStore.updateState(new SurveyScreenPresentationState.Loading(mStateStore.getCurrentState().getSurveyPM()));
            }

            @Override
            public void onSuccess(@NonNull String s) {
                    mStateStore.updateEffect(new SurveyScreenPresentationState.Effect.SubmitResultDone(s));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("onErrororrr", e.getMessage());
                mStateStore.updateState(new SurveyScreenPresentationState.Error(new SubmitAnswerError(), (mStateStore.getCurrentState()).getSurveyPM()));
            }
        });
    }
    
}

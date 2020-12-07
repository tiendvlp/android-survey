package com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.controller;

import android.util.Log;

import com.devlogs.osg.apolo.fptsurvey.domain.entity.SurveyQuestionEntity;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.presentationmodel.QuestionPM;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.screenstate.SurveyScreenPresentationState;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.screenstate.SurveyScreenPresentationStateStore;
import com.devlogs.osg.apolo.fptsurvey.surveyquestion.GetAllQuestionInSurveyUseCaseSync;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

class GetQuestionController {
    private SurveyScreenPresentationStateStore mStateStore;
    private GetAllQuestionInSurveyUseCaseSync mGetAllQuesUseCase;

    @Inject
    public GetQuestionController(SurveyScreenPresentationStateStore stateStore, GetAllQuestionInSurveyUseCaseSync getAllQuesUseCase) {
        this.mStateStore = stateStore;
        this.mGetAllQuesUseCase = getAllQuesUseCase;
    }

    public void getAllQuestion (String surveyId) {
        mGetAllQuesUseCase.execute(surveyId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Consumer<List<SurveyQuestionEntity>>() {
                    @Override
                    public void accept(List<SurveyQuestionEntity> surveyQuestionEntities) throws Throwable {
                        List<QuestionPM> questionPMS = new ArrayList();
                        Thread.sleep(2000);
                        for (SurveyQuestionEntity question : surveyQuestionEntities) {
                            questionPMS.add(new QuestionPM(question.getQuestion(), question.getAnswers()));
                        }
                        Log.d("GetQuestionController", "duam" + questionPMS.get(0).getAnswers().length + "");
                        mStateStore.updateState(new SurveyScreenPresentationState.Finished(mStateStore.getCurrentState().getSurveyPM(), questionPMS));
                    }
                },
                new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d("GetQuestionController", throwable.getMessage() + "");
                        mStateStore.updateState(new SurveyScreenPresentationState.Error(throwable,mStateStore.getCurrentState().getSurveyPM()));
                    }
                }
        );
    }
}

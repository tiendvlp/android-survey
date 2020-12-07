package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.controller;

import android.util.Log;

import com.devlogs.osg.apolo.fptsurvey.domain.entity.SurveyEntity;
import com.devlogs.osg.apolo.fptsurvey.domain.entity.SurveyTopicEntity;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.SurveyPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.SurveyTopicPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.screenstate.MainScreenPresentationState;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.screenstate.MainScreenPresentationStateStore;
import com.devlogs.osg.apolo.fptsurvey.survey.GetAllSurveyUseCaseSync;
import com.devlogs.osg.apolo.fptsurvey.surveytopic.GetAllSurveyTopicUseCaseSync;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GetAllSurveyController {
    private MainScreenPresentationStateStore mStateStore;
    private GetAllSurveyUseCaseSync mGetAllSurveyUseCase;
    private GetAllSurveyTopicUseCaseSync mGetAllSurveyTopic;

    @Inject
    public GetAllSurveyController (GetAllSurveyUseCaseSync getAllSurveyUseCaseSync,GetAllSurveyTopicUseCaseSync getAllSurveyTopic, MainScreenPresentationStateStore mainScreenPresentationStateStore) {
        mStateStore = mainScreenPresentationStateStore;
        mGetAllSurveyUseCase = getAllSurveyUseCaseSync;
        mGetAllSurveyTopic = getAllSurveyTopic;
    }

    public void getAllSurvey() {
        mGetAllSurveyTopic.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SurveyTopicEntity>>() {
                    @Override
                    public void accept(List<SurveyTopicEntity> surveyTopicEntities) throws Throwable {
                        ArrayList<SurveyTopicPM> surveyTopicPMS  = new ArrayList();
                        for (SurveyTopicEntity surveyTopic : surveyTopicEntities) {
                            surveyTopicPMS.add(new SurveyTopicPM(surveyTopic.getTitle(), surveyTopic.getProgress(), surveyTopic.getId()));
                        }
                        Log.d("GetAllSurveyController", "done topic: " + surveyTopicPMS.size());
                        mGetAllSurveyUseCase.execute().observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<SurveyEntity>>() {
                            @Override
                            public void accept(List<SurveyEntity> surveyEntities) throws Throwable {
                                ArrayList<SurveyPM> surveyPresentationModel = new ArrayList();
                                for (SurveyEntity surveyEntity : surveyEntities) {
                                    surveyPresentationModel.add(new SurveyPM(surveyEntity.getTitle(),surveyEntity.getNumberOfQuestion(), surveyEntity.getDescription(), surveyEntity.getTopicId(), surveyEntity.getId()));
                                }
                                Log.d("GetAllSurveyController", "done survey: " + surveyPresentationModel.size());
                                mStateStore.updateEffect(new MainScreenPresentationState.MainScreenPresentationEffect.SurveyLoaded(surveyPresentationModel, surveyTopicPMS));
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {
                                mStateStore.updateState(new MainScreenPresentationState.Error(throwable.getMessage()
                                ));
                            }
                        });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        mStateStore.updateState(new MainScreenPresentationState.Error(throwable.getMessage()));
                    }
                });
    }
}

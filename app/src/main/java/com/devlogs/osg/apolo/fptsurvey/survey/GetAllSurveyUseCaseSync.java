package com.devlogs.osg.apolo.fptsurvey.survey;


import com.devlogs.osg.apolo.fptsurvey.domain.data.AuthDisk;
import com.devlogs.osg.apolo.fptsurvey.domain.data.SurveyNetwork;
import com.devlogs.osg.apolo.fptsurvey.domain.data.model.SurveyNetworkModel;
import com.devlogs.osg.apolo.fptsurvey.domain.entity.SurveyEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GetAllSurveyUseCaseSync {
    private SurveyNetwork mSurveyNetwork;
    private AuthDisk mAuth;

    @Inject
    public GetAllSurveyUseCaseSync(SurveyNetwork mSurveyNetwork, AuthDisk authDisk) {
        this.mSurveyNetwork = mSurveyNetwork;
        mAuth = authDisk;
    }

    public Single<List<SurveyEntity>> execute () {
        return Single.create(new SingleOnSubscribe<List<SurveyEntity>>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<List<SurveyEntity>> emitter) throws Throwable {
                mAuth.getAccessToken().subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Throwable {
                        mSurveyNetwork.getAllSurvey(s).map((surveyNetworkModels)-> {
                            ArrayList<SurveyEntity> result = new ArrayList();
                            for (SurveyNetworkModel survey : surveyNetworkModels) {
                                result.add(new SurveyEntity(survey.getId(), survey.getName(), survey.getDescription(), survey.getNumberOfQuestion(), survey.getTopicId()));
                            }
                            return result;
                        }).subscribe(new Consumer<ArrayList<SurveyEntity>>() {
                            @Override
                            public void accept(ArrayList<SurveyEntity> surveyEntities) throws Throwable {
                                emitter.onSuccess(surveyEntities);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {
                                emitter.onError(throwable);
                            }
                        });
                    }
                });
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}

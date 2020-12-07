package com.devlogs.osg.apolo.fptsurvey.surveyresult;

import com.devlogs.osg.apolo.fptsurvey.domain.data.AuthDisk;
import com.devlogs.osg.apolo.fptsurvey.domain.data.SurveyResultNetwork;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PublishResultUseCaseSync {
    private SurveyResultNetwork mSurveyResultNetwork;
    private AuthDisk mAuth;

    @Inject
    public PublishResultUseCaseSync(SurveyResultNetwork surveyResultNetwork, AuthDisk authDisk) {
        mSurveyResultNetwork = surveyResultNetwork;
        mAuth = authDisk;
    }

    public Single<String> execute (String surveyId, List<Integer> answer ) {
        return mAuth.getAccessToken()
                .flatMap(new Function<String, SingleSource<String>>() {
                    @Override
                    public SingleSource<String> apply(String s) throws Throwable {
                        return mSurveyResultNetwork.publishResult(surveyId, answer, s);
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }
}

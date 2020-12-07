package com.devlogs.osg.apolo.fptsurvey.survey;

import com.devlogs.osg.apolo.fptsurvey.domain.data.SurveyNetwork;
import com.devlogs.osg.apolo.fptsurvey.domain.entity.SurveyEntity;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

class GetSurveyByIdUseCaseSync {
    private SurveyNetwork mSurveyNetwork;

    @Inject
    public GetSurveyByIdUseCaseSync(SurveyNetwork mSurveyNetwork) {
        this.mSurveyNetwork = mSurveyNetwork;
    }

    private Single<SurveyEntity> execute (String idToken, String id) {
        return mSurveyNetwork.getSurveyById(idToken, id).map((surveyNetworkModel) -> {
            return new SurveyEntity(surveyNetworkModel.getId(), surveyNetworkModel.getName(), surveyNetworkModel.getDescription(), surveyNetworkModel.getNumberOfQuestion(), surveyNetworkModel.getTopicId());
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}

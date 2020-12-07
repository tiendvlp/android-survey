package com.devlogs.osg.apolo.fptsurvey.domain.data;

import com.devlogs.osg.apolo.fptsurvey.domain.data.model.SurveyNetworkModel;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface SurveyNetwork {
    Single<List<SurveyNetworkModel>> getAllSurvey (String idToken);
    Single<SurveyNetworkModel> getSurveyById (String idToken, String id);
}

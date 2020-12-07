package com.devlogs.osg.apolo.fptsurvey.domain.data;

import com.devlogs.osg.apolo.fptsurvey.domain.data.model.SurveyNetworkModel;
import com.devlogs.osg.apolo.fptsurvey.domain.data.model.SurveyQuestionNetworkModel;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface SurveyQuestionNetwork {
    Single<List<SurveyQuestionNetworkModel>> getSurveyQuestion (String idToken, String id);
}

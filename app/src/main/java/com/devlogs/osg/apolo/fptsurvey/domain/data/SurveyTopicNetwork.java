package com.devlogs.osg.apolo.fptsurvey.domain.data;

import com.devlogs.osg.apolo.fptsurvey.domain.data.model.SurveyTopicNetworkModel;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface SurveyTopicNetwork {
    Single<List<SurveyTopicNetworkModel>> getAllSurveyTopic (String accessToken);
}

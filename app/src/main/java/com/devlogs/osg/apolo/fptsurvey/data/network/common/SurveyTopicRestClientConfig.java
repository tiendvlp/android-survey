package com.devlogs.osg.apolo.fptsurvey.data.network.common;

import com.devlogs.osg.apolo.fptsurvey.domain.data.model.SurveyTopicNetworkModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;

public interface SurveyTopicRestClientConfig {
    @GET("surveytopic/getall")
    public Call<List<SurveyTopicNetworkModel>> getAllSurveyTopic (@HeaderMap Map<String, String> header);
}

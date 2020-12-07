package com.devlogs.osg.apolo.fptsurvey.data.network.common;

import com.devlogs.osg.apolo.fptsurvey.domain.data.model.SurveyNetworkModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Url;

public interface SurveyRestClientConfig {
    @GET("survey/getallsurveys")
    Call<List<SurveyNetworkModel>> getAllSurvey (@HeaderMap Map<String,String> header) ;
}

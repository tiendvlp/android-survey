package com.devlogs.osg.apolo.fptsurvey.data.network.common;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Url;

public interface SurveyResultRestClientConfig {

    @GET()
    Call<ResponseBody> getSurveyResult(@Url String url, @HeaderMap HashMap<String, String> header);
}

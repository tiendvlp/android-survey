package com.devlogs.osg.apolo.fptsurvey.data.network.common;

import com.devlogs.osg.apolo.fptsurvey.domain.data.model.QuickQuestionNetworkModel;
import com.devlogs.osg.apolo.fptsurvey.domain.data.model.StatusNetworkModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface StatusRestClientConfig {

    @GET("status/getAllStatus")
    Call<List<StatusNetworkModel>> getAllStatus();


}

package com.devlogs.osg.apolo.fptsurvey.data.network.common;

import com.devlogs.osg.apolo.fptsurvey.domain.data.model.AuthNetworkModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;

public interface AuthRestClientConfig {
    @GET("auth/login")
    Call<AuthNetworkModel> loginWithFptGmail(@HeaderMap Map<String, String> header);
}

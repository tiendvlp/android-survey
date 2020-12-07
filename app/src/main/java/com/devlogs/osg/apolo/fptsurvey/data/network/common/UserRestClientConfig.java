package com.devlogs.osg.apolo.fptsurvey.data.network.common;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;

public interface UserRestClientConfig {
    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("user/getuser")
    Call<GetUserResBody> getCurrentUser(@HeaderMap Map<String, String> header);

    // the response-body
    public class GetUserResBody {
        public String name;
        public String studentId;
        public String email;
        public String campus;
        public int admission;
        public String pictureUrl;
    }
}

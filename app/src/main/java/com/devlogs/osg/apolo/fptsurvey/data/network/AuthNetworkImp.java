package com.devlogs.osg.apolo.fptsurvey.data.network;

import android.util.Log;

import com.devlogs.osg.apolo.fptsurvey.data.network.common.AuthRestClientConfig;
import com.devlogs.osg.apolo.fptsurvey.domain.data.AuthNetwork;
import com.devlogs.osg.apolo.fptsurvey.domain.data.model.AuthNetworkModel;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AuthNetworkImp implements AuthNetwork {
    private Retrofit mRetrofit;

    @Inject
    public AuthNetworkImp(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    @Override
    public Single<AuthNetworkModel> loginWithFptGmail(String googleIdToken) {
        return Single.create(new SingleOnSubscribe<AuthNetworkModel>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<AuthNetworkModel> emitter) throws Throwable {
                AuthRestClientConfig client = mRetrofit.create(AuthRestClientConfig.class);
                Map<String, String> header = new HashMap<String, String>();
                String bearerAuth = "Bearer " + googleIdToken;
                Log.d("AuthNetworkImp", bearerAuth);
                header.put("authorization",bearerAuth);
                client.loginWithFptGmail(header).enqueue(new Callback<AuthNetworkModel>() {
                    @Override
                    public void onResponse(Call<AuthNetworkModel> call, Response<AuthNetworkModel> response) {
                        if (response.code() == 200) {
                            emitter.onSuccess(response.body());
                        } else {
                            emitter.onError(new Error("Login Failed"));
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthNetworkModel> call, Throwable t) {
                        emitter.onError(t);
                    }
                });
            }
        });
      }
}

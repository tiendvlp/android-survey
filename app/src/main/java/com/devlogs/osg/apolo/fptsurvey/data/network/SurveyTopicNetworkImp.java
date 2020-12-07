package com.devlogs.osg.apolo.fptsurvey.data.network;

import android.util.Log;

import com.devlogs.osg.apolo.fptsurvey.data.network.common.SurveyTopicRestClientConfig;
import com.devlogs.osg.apolo.fptsurvey.domain.data.SurveyTopicNetwork;
import com.devlogs.osg.apolo.fptsurvey.domain.data.model.SurveyTopicNetworkModel;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SurveyTopicNetworkImp implements SurveyTopicNetwork {
    private Retrofit mRetrofit;

    public SurveyTopicNetworkImp (Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    @Override
    public Single<List<SurveyTopicNetworkModel>> getAllSurveyTopic(String accessToken) {
        return Single.create(new SingleOnSubscribe<List<SurveyTopicNetworkModel>>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<List<SurveyTopicNetworkModel>> emitter) throws Throwable {
                HashMap<String, String> header = new HashMap<String, String>();
                header.put("Authorization","Bearer " + accessToken);

                SurveyTopicRestClientConfig clientCall = mRetrofit.create(SurveyTopicRestClientConfig.class);
                clientCall.getAllSurveyTopic(header)
                        .enqueue(new Callback<List<SurveyTopicNetworkModel>>() {
                            @Override
                            public void onResponse(Call<List<SurveyTopicNetworkModel>> call, Response<List<SurveyTopicNetworkModel>> response) {
                                if (response.code() == 200) {
                                    emitter.onSuccess(response.body());
                                } else {
                                    emitter.onError(new Error("Can not get survey topic"));
                                }
                            }

                            @Override
                            public void onFailure(Call<List<SurveyTopicNetworkModel>> call, Throwable t) {
                                emitter.onError(t);
                            }
                        });
            }
        });
    }
}

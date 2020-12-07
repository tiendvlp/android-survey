package com.devlogs.osg.apolo.fptsurvey.data.network;

import android.util.Log;

import com.devlogs.osg.apolo.fptsurvey.data.network.common.SurveyRestClientConfig;
import com.devlogs.osg.apolo.fptsurvey.domain.data.SurveyNetwork;
import com.devlogs.osg.apolo.fptsurvey.domain.data.model.SurveyNetworkModel;

import java.util.HashMap;
import java.util.List;
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

public class SurveyNetworkImp implements SurveyNetwork {
    private Retrofit mRetrofit;

    @Inject
    public SurveyNetworkImp(Retrofit mRetrofit) {
        this.mRetrofit = mRetrofit;
    }

    @Override
    public Single<List<SurveyNetworkModel>> getAllSurvey(String idToken) {
        return Single.create(new SingleOnSubscribe<List<SurveyNetworkModel>>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<List<SurveyNetworkModel>> emitter) throws Throwable {
                SurveyRestClientConfig client = mRetrofit.create(SurveyRestClientConfig.class);
                Map<String, String> header = new HashMap<String, String>();
                header.put("Authorization","Bearer " + idToken);
                client.getAllSurvey(header).enqueue(new Callback<List<SurveyNetworkModel>>() {
                    @Override
                    public void onResponse(Call<List<SurveyNetworkModel>> call, Response<List<SurveyNetworkModel>> response) {
                        if (response.code() == 200) {
                            emitter.onSuccess(response.body());
                            Log.d("SurveyNetwork", response.body().size() + "");
                        } else {
                            emitter.onError(new Error("Error code: " + response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SurveyNetworkModel>> call, Throwable t) {
                        emitter.onError(t);
                    }
                });
            }
        });
    }

    @Override
    public Single<SurveyNetworkModel> getSurveyById(String idToken, String id) {
        return null;
    }
}

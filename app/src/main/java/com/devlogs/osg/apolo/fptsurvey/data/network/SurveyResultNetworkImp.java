package com.devlogs.osg.apolo.fptsurvey.data.network;

import android.util.Log;

import com.devlogs.osg.apolo.fptsurvey.data.network.common.SurveyResultClientConfig;
import com.devlogs.osg.apolo.fptsurvey.data.network.common.SurveyResultRestClientConfig;
import com.devlogs.osg.apolo.fptsurvey.domain.data.SurveyResultNetwork;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SurveyResultNetworkImp implements SurveyResultNetwork {
    private Retrofit mRetrofit;

    @Inject
    public SurveyResultNetworkImp (Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    public Single<String> publishResult(String surveyId, List<Integer> answer, String idToken) {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<String> emitter) throws Throwable {
                HashMap<String, String> header = new HashMap<String, String>();
                header.put("Authorization","Bearer " + idToken);

                SurveyResultClientConfig client = mRetrofit.create(SurveyResultClientConfig.class);

                client.publishResult(header,new SurveyResultClientConfig.PublishResultReqBody(surveyId, answer)).enqueue(new Callback<SurveyResultClientConfig.PublishSurveyResultRespondModel>() {
                    @Override
                    public void onResponse(Call<SurveyResultClientConfig.PublishSurveyResultRespondModel> call, Response<SurveyResultClientConfig.PublishSurveyResultRespondModel> response) {
                        if (response.code() == 200) {
                            emitter.onSuccess(response.body().getSurveyResultId());
                        } else {
                            emitter.onError(new Error("Publish Result failed"));
                        }
                    }

                    @Override
                    public void onFailure(Call<SurveyResultClientConfig.PublishSurveyResultRespondModel> call, Throwable t) {
                        emitter.onError(t);
                    }
                });
            }
        });
    }

    public Single<String> getSurveyResult (String resultId, String idToken) {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<String> emitter) throws Throwable {
                HashMap<String, String> header = new HashMap<String, String>();
                header.put("Authorization","Bearer " + idToken);

            SurveyResultRestClientConfig client = mRetrofit.create(SurveyResultRestClientConfig.class);
            client.getSurveyResult("survey/resultv2/getResult?id=" + resultId, header).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 200) {
                        try {
                            emitter.onSuccess(response.body().string());
                            Log.d("SurveyResult", response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                            emitter.onError(e);
                        }
                        return;
                    }

                    emitter.onError(new Error("Failed" + response.code()));
                    return;
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    emitter.onError(t);
                }
            });
            }
        });
    }
}

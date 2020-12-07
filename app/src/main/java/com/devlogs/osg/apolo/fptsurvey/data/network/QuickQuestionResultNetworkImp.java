package com.devlogs.osg.apolo.fptsurvey.data.network;

import com.devlogs.osg.apolo.fptsurvey.data.network.common.QuickQuestionRestClientConfig;
import com.devlogs.osg.apolo.fptsurvey.domain.data.QuickQuestionResultNetwork;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.CompletableOnSubscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class QuickQuestionResultNetworkImp implements QuickQuestionResultNetwork {
    private Retrofit mRetrofit;

    public QuickQuestionResultNetworkImp (Retrofit retrofit) {
        mRetrofit =retrofit;
    }

    @Override
    public Completable pushResult(String questionId, int answer, String email) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(@NonNull CompletableEmitter emitter) throws Throwable {
                QuickQuestionRestClientConfig client = mRetrofit.create(QuickQuestionRestClientConfig.class);

                client.addNewResult(new QuickQuestionRestClientConfig.AddNewResultReqBody(questionId, answer, email)).enqueue(new Callback<QuickQuestionRestClientConfig.AddNewResultRespondModel>() {
                    @Override
                    public void onResponse(Call<QuickQuestionRestClientConfig.AddNewResultRespondModel> call, Response<QuickQuestionRestClientConfig.AddNewResultRespondModel> response) {
                        if (response.code() == 200) {
                            emitter.onComplete();
                        } else {
                            emitter.onError(new Error("Push result failed"));
                        }
                    }

                    @Override
                    public void onFailure(Call<QuickQuestionRestClientConfig.AddNewResultRespondModel> call, Throwable t) {
                            emitter.onError(t);
                    }
                });
            }
        });
    }
}

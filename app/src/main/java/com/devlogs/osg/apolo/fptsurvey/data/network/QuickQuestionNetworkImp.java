package com.devlogs.osg.apolo.fptsurvey.data.network;

import com.devlogs.osg.apolo.fptsurvey.data.network.common.QuickQuestionRestClientConfig;
import com.devlogs.osg.apolo.fptsurvey.domain.data.QuickQuestionNetwork;
import com.devlogs.osg.apolo.fptsurvey.domain.data.model.QuickQuestionNetworkModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class QuickQuestionNetworkImp implements QuickQuestionNetwork {
    private Retrofit mRetrofit;

    @Inject
    public QuickQuestionNetworkImp(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    public Single<List<QuickQuestionNetworkModel>> getAllQuickQuestion () {
        return Single.create(new SingleOnSubscribe<List<QuickQuestionNetworkModel>>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<List<QuickQuestionNetworkModel>> emitter) throws Throwable {
                QuickQuestionRestClientConfig client = mRetrofit.create(QuickQuestionRestClientConfig.class);
                client.getAllQuickQuestion().enqueue(new Callback<List<QuickQuestionNetworkModel>>() {
                    @Override
                    public void onResponse(Call<List<QuickQuestionNetworkModel>> call, Response<List<QuickQuestionNetworkModel>> response) {
                        if (response.code() == 200) {
                            emitter.onSuccess(response.body());
                        } else {
                            emitter.onError(new Error("Get quick question failed"));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<QuickQuestionNetworkModel>> call, Throwable t) {
                        emitter.onError(t);
                    }
                });
            }
        });
    }
}

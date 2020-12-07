package com.devlogs.osg.apolo.fptsurvey.data.network;


import com.devlogs.osg.apolo.fptsurvey.data.network.common.StatusRestClientConfig;
import com.devlogs.osg.apolo.fptsurvey.domain.data.StatusNetwork;
import com.devlogs.osg.apolo.fptsurvey.domain.data.model.StatusNetworkModel;
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

public class StatusNetworkImp implements StatusNetwork {
    private Retrofit mRetrofit;

    @Inject
    public StatusNetworkImp(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    public Single<List<StatusNetworkModel>> getAllStatus () {
        return Single.create(new SingleOnSubscribe<List<StatusNetworkModel>>() {
            @Override
            public void subscribe(@NonNull final SingleEmitter<List<StatusNetworkModel>> emitter) throws Throwable {
                StatusRestClientConfig client = mRetrofit.create(StatusRestClientConfig.class);

                client.getAllStatus().enqueue(new Callback<List<StatusNetworkModel>>() {
                    @Override
                    public void onResponse(Call<List<StatusNetworkModel>> call, Response<List<StatusNetworkModel>> response) {
                        if (response.code() == 200) {
                            emitter.onSuccess(response.body());
                        } else {
                            emitter.onError(new Error("Get Status failed"));
                        }

                    }

                    @Override
                    public void onFailure(Call<List<StatusNetworkModel>> call, Throwable t) {
                            emitter.onError(t);
                    }
                });
            }
        });
    }
}

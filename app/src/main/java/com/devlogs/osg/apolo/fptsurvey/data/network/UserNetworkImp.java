package com.devlogs.osg.apolo.fptsurvey.data.network;

import com.devlogs.osg.apolo.fptsurvey.data.network.common.UserRestClientConfig;
import com.devlogs.osg.apolo.fptsurvey.domain.data.UserNetwork;
import com.devlogs.osg.apolo.fptsurvey.domain.data.model.UserNetworkModel;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserNetworkImp implements UserNetwork {
    private Retrofit mRetrofit;

    @Inject
    public UserNetworkImp(Retrofit mRetrofit) {
        this.mRetrofit = mRetrofit;
    }

    @Override
    public Single<UserNetworkModel> getUserInfo(String idToken) {
        return Single.create(new SingleOnSubscribe<UserNetworkModel>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<UserNetworkModel> emitter) throws Throwable {
                UserRestClientConfig client = mRetrofit.create(UserRestClientConfig.class);
                HashMap<String, String> header = new HashMap<String, String>();
                header.put("Authorization","Bearer " + idToken);
                client.getCurrentUser(header).enqueue(new Callback<UserRestClientConfig.GetUserResBody>() {
                    @Override
                    public void onResponse(Call<UserRestClientConfig.GetUserResBody> call, Response<UserRestClientConfig.GetUserResBody> response) {
                        if (response.code() == 200) {
                            emitter.onSuccess(new UserNetworkModel(
                                    response.body().name,
                                    response.body().studentId,
                                    response.body().email,
                                    response.body().campus,
                                    response.body().admission,
                                    response.body().pictureUrl
                            ));
                        } else if (response.code() == 400) {
                            emitter.onError(new Error("Your email is not belong to fpt university"));
                        }
                    }

                    @Override
                    public void onFailure(Call<UserRestClientConfig.GetUserResBody> call, Throwable t) {
                        emitter.onError(t);
                    }
                });
            }
        });
    }
}

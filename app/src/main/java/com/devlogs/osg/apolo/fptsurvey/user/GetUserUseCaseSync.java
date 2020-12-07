package com.devlogs.osg.apolo.fptsurvey.user;

import android.util.Log;

import com.devlogs.osg.apolo.fptsurvey.domain.data.AuthDisk;
import com.devlogs.osg.apolo.fptsurvey.domain.data.AuthNetwork;
import com.devlogs.osg.apolo.fptsurvey.domain.data.UserDisk;
import com.devlogs.osg.apolo.fptsurvey.domain.data.UserNetwork;
import com.devlogs.osg.apolo.fptsurvey.domain.data.model.UserNetworkModel;
import com.devlogs.osg.apolo.fptsurvey.domain.entity.UserEntity;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;

public class GetUserUseCaseSync {
    private AuthNetwork mAuthNetwork;
    private UserNetwork mUserNetwork;
    private UserDisk mUserDisk;
    private AuthDisk mAuthDisk;

    @Inject
    public GetUserUseCaseSync(AuthNetwork authNetwork, UserNetwork userNetwork, AuthDisk authDisk, UserDisk userDisk) {
        this.mAuthNetwork = authNetwork;
        this.mUserDisk = userDisk;
        this.mUserNetwork = userNetwork;
        this.mAuthDisk = authDisk;
    }

    public Single<UserEntity> execute() {
        return Single.create(new SingleOnSubscribe<UserEntity>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<UserEntity> emitter) throws Throwable {
                mAuthDisk.getAccessToken().subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Throwable {
                        mUserDisk.getUser().subscribe(new Consumer<UserEntity>() {
                            @Override
                            public void accept(UserEntity userEntity) throws Throwable {
                                emitter.onSuccess(userEntity);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {
                                Log.d("GetUserInfoC", "Donee: " + throwable.getMessage());

                                mUserNetwork.getUserInfo(s)
                                        .subscribe(new Consumer<UserNetworkModel>() {
                                            @Override
                                            public void accept(UserNetworkModel userNetworkModel) throws Throwable {
                                                Log.d("GetUserInfoC", "Donee: " + userNetworkModel.getCampus());
                                                emitter.onSuccess(new UserEntity(
                                                        userNetworkModel.getName(),
                                                        userNetworkModel.getStudentId(),
                                                        userNetworkModel.getEmail(),
                                                        userNetworkModel.getCampus(),
                                                        userNetworkModel.getAdmission(),
                                                        userNetworkModel.getPictureUrl()));
                                            }
                                        }, new Consumer<Throwable>() {
                                            @Override
                                            public void accept(Throwable throwable) throws Throwable {
                                                emitter.onError(throwable);
                                            }
                                        });
                            }
                        });

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d("GetUserInfoC", "Failed: " + throwable.getMessage());
                        emitter.onError(throwable);
                    }
                });
            }
        });
    }

}

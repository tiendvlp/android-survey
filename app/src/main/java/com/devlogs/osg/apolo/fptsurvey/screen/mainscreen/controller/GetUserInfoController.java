package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.controller;

import android.util.Log;

import com.devlogs.osg.apolo.fptsurvey.domain.entity.UserEntity;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.UserPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.screenstate.MainScreenPresentationState;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.screenstate.MainScreenPresentationStateStore;
import com.devlogs.osg.apolo.fptsurvey.user.GetUserUseCaseSync;


import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;

class GetUserInfoController {
    private GetUserUseCaseSync mGetUserUseCase;
    private MainScreenPresentationStateStore mStateStore;

    @Inject
    public GetUserInfoController (GetUserUseCaseSync getUserUseCaseSync, MainScreenPresentationStateStore stateStore) {
        mGetUserUseCase = getUserUseCaseSync;
        mStateStore = stateStore;
    }

    public void getUser () {
        mGetUserUseCase.execute().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserEntity>() {
            @Override
            public void accept(UserEntity userEntity) {
                UserPM user = new UserPM(
                        userEntity.getName(),
                        userEntity.getStudentId(),
                        userEntity.getEmail(),
                        userEntity.getCampus(),
                        userEntity.getAdmission(),
                        userEntity.getPictureUrl());
                Log.d("GetUserInfoC", "Donee: " + user.getCampus());
                mStateStore.updateEffect(new MainScreenPresentationState.MainScreenPresentationEffect.UserLoaded(user));

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Throwable {
                mStateStore.updateState(new MainScreenPresentationState.Error(throwable.getMessage()));
            }
        });
    }
}

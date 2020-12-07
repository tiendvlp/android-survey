package com.devlogs.osg.apolo.fptsurvey.screen.loginscreen.controller;

import android.util.Log;

import com.devlogs.osg.apolo.fptsurvey.login.LoginWithFptGmailUseCase;
import com.devlogs.osg.apolo.fptsurvey.screen.loginscreen.screenstate.LoginScreenState;
import com.devlogs.osg.apolo.fptsurvey.screen.loginscreen.screenstate.LoginStateStore;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;

class LoginController  {
    private LoginWithFptGmailUseCase mLoginFptUseCase;
    private LoginStateStore mStateStore;

    @Inject
    public LoginController(LoginWithFptGmailUseCase mLoginFptUseCase, LoginStateStore mStateStore) {
        this.mLoginFptUseCase = mLoginFptUseCase;
        this.mStateStore = mStateStore;
    }

    public void loginWithFptGmail (String googleIdToken) {
        mStateStore.updateState(new LoginScreenState.LoadingState());
        mLoginFptUseCase.execute(googleIdToken).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("LoginController", "OnSuccess");
                        mStateStore.updateState(new LoginScreenState.LoggedInState());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("LoginController", "OnError");
                        mStateStore.updateState(new LoginScreenState.NotLoggedInState());
                    }
                }
        );
    }
}

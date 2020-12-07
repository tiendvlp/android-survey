package com.devlogs.osg.apolo.fptsurvey.login;

import com.devlogs.osg.apolo.fptsurvey.domain.data.AuthDisk;
import com.devlogs.osg.apolo.fptsurvey.domain.data.AuthNetwork;
import com.devlogs.osg.apolo.fptsurvey.domain.data.model.AuthNetworkModel;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.CompletableOnSubscribe;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginWithFptGmailUseCase {
    private AuthNetwork mLogin;
    private AuthDisk mDisk;

    @Inject
    public LoginWithFptGmailUseCase(AuthNetwork login, AuthDisk authDisk) {
        mLogin = login;
        mDisk = authDisk;
    }

    public Completable execute(String googleTokenId) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(@NonNull CompletableEmitter emitter) {
                mLogin.loginWithFptGmail(googleTokenId).subscribe(new Consumer<AuthNetworkModel>() {
                    @Override
                    public void accept(AuthNetworkModel authNetworkModel) {
                        mDisk.saveToken(new AuthDisk.SaveTokenParam(authNetworkModel.getAccessToken()))
                        .subscribe(new Action() {
                            @Override
                            public void run() {
                                emitter.onComplete();
                            }
                        });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        emitter.onError(throwable);
                    }
                });
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(Schedulers.newThread());
    }

}


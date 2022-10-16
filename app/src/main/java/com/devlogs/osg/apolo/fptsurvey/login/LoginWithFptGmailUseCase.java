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
        return Completable.create(emitter -> mLogin.loginWithFptGmail(googleTokenId)
                .subscribe(authNetworkModel -> mDisk.saveToken(new AuthDisk.SaveTokenParam(authNetworkModel.getAccessToken()))
                .subscribe(() -> emitter.onComplete()), throwable -> emitter.onError(throwable)))
                .subscribeOn(AndroidSchedulers.mainThread()).observeOn(Schedulers.newThread());
    }
}


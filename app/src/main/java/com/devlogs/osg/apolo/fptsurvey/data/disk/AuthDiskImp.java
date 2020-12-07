package com.devlogs.osg.apolo.fptsurvey.data.disk;

import android.content.SharedPreferences;
import com.devlogs.osg.apolo.fptsurvey.domain.data.AuthDisk;
import javax.inject.Inject;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.CompletableOnSubscribe;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;

public class AuthDiskImp implements AuthDisk {
    private SharedPreferences mSharedPreferences;

    public AuthDiskImp(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    @Override
    public Completable saveToken(SaveTokenParam param) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(@NonNull CompletableEmitter emitter) throws Throwable {
                mSharedPreferences.edit().putString("accessToken", param.accessToken).apply();
                emitter.onComplete();
            }
        });
    }

    @Override
    public Single<String> getAccessToken() {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<String> emitter) throws Throwable {
                String accessToken = mSharedPreferences.getString("accessToken", "");
                if (accessToken == null || accessToken.isEmpty()) {
                    emitter.onError(new Error("Access token null"));
                }
                emitter.onSuccess(accessToken);
            }
        });
    }
    public void clear () {
        mSharedPreferences.edit().remove("accessToken").apply();
    }

    @Override
    public Single<String> getRefreshToken() throws Exception {
        throw new Exception("Not implemented");
    }
}

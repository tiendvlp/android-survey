package com.devlogs.osg.apolo.fptsurvey.data.disk;

import android.content.SharedPreferences;

import com.devlogs.osg.apolo.fptsurvey.domain.data.UserDisk;
import com.devlogs.osg.apolo.fptsurvey.domain.entity.UserEntity;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.CompletableOnSubscribe;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserDiskImp implements UserDisk {
    private SharedPreferences mSharedPreferences;


    public UserDiskImp (SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    @Override
    public Completable saveUser(UserEntity userEntity) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(@NonNull CompletableEmitter emitter) throws Throwable {
                mSharedPreferences.edit()
                        .putString("userName", userEntity.getName())
                        .putString("studentId", userEntity.getStudentId())
                        .putString("email", userEntity.getEmail())
                        .putString("campus", userEntity.getCampus())
                        .putInt("admission", userEntity.getAdmission())
                        .putString("pictureUrl", userEntity.getPictureUrl())
                        .apply();

                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<UserEntity> getUser() {
        return Single.create(new SingleOnSubscribe<UserEntity>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<UserEntity> emitter) throws Throwable {
                String name = mSharedPreferences.getString("userName", null);
                String studentId = mSharedPreferences.getString("studentId", null);
                String email = mSharedPreferences.getString("email", null);
                String campus = mSharedPreferences.getString("campus", null);
                int admission = mSharedPreferences.getInt("admission", -1);
                String pictureUrl = mSharedPreferences.getString("pictureUrl", null);

                if (name == null || studentId == null || email == null || campus == null || admission == -1 || pictureUrl == null) {
                    emitter.onError(new Error("404: User data not found"));
                    return;
                }

                emitter.onSuccess(new UserEntity(name, studentId, email, campus, admission, pictureUrl));
            }
        });
    }

    @Override
    public void clear() {
        mSharedPreferences.edit().remove("userName")
        .remove("studentId")
        .remove("email")
        .remove("campus")
        .remove("admission")
        .remove("pictureUrl")
        .apply();
    }
}

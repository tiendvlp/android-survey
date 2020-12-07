package com.devlogs.osg.apolo.fptsurvey.domain.data;

import com.devlogs.osg.apolo.fptsurvey.domain.entity.UserEntity;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface UserDisk {
    Completable saveUser (UserEntity userEntity);
    Single<UserEntity> getUser ();
    void clear ();
}

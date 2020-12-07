package com.devlogs.osg.apolo.fptsurvey.domain.data;

import com.devlogs.osg.apolo.fptsurvey.domain.data.model.UserNetworkModel;
import com.devlogs.osg.apolo.fptsurvey.domain.entity.UserEntity;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface UserNetwork {
    Single<UserNetworkModel> getUserInfo(String idToken);
}

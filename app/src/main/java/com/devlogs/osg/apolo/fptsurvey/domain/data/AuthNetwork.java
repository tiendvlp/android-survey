package com.devlogs.osg.apolo.fptsurvey.domain.data;

import com.devlogs.osg.apolo.fptsurvey.domain.data.model.AuthNetworkModel;

import io.reactivex.rxjava3.core.Single;

public interface AuthNetwork {
    Single<AuthNetworkModel> loginWithFptGmail (String googleIdToken);
}

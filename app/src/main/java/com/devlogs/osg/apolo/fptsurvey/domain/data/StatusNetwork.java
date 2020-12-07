package com.devlogs.osg.apolo.fptsurvey.domain.data;

import com.devlogs.osg.apolo.fptsurvey.domain.data.model.StatusNetworkModel;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface StatusNetwork {
    public Single<List<StatusNetworkModel>> getAllStatus ();
}

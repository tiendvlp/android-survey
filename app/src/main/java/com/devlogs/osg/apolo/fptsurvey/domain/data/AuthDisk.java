package com.devlogs.osg.apolo.fptsurvey.domain.data;

import com.devlogs.osg.apolo.fptsurvey.domain.data.model.AuthDiskModel;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface AuthDisk {
    Completable saveToken (SaveTokenParam param);
    Single<String> getAccessToken ();
    Single<String> getRefreshToken () throws Exception;
    void clear ();

    class SaveTokenParam {
        public String accessToken;

        public SaveTokenParam(String accessToken) {
            this.accessToken = accessToken;
        }
    }
}

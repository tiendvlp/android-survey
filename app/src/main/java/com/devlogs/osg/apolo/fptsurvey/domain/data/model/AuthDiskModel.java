package com.devlogs.osg.apolo.fptsurvey.domain.data.model;

public class AuthDiskModel {
    private String accessToken;

    public AuthDiskModel(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}

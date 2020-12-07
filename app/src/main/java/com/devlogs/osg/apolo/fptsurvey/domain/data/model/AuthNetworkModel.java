package com.devlogs.osg.apolo.fptsurvey.domain.data.model;

public class AuthNetworkModel {
    private String accessToken;

    public AuthNetworkModel(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}

package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel;

public class StatusPM {
    private String type;
    private String statusBody;

    public StatusPM(String type, String statusBody) {
        this.type = type;
        this.statusBody = statusBody;
    }

    public String getType() {
        return type;
    }

    public String getStatusBody() {
        return statusBody;
    }
}

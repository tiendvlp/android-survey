package com.devlogs.osg.apolo.fptsurvey.screen.common.dialog.showinguserinfo;

import java.io.Serializable;

public class UserInfoDialogPM implements Serializable {
    private String userName;
    private String studentId;
    private String avatarUrl;

    public UserInfoDialogPM (String name, String studentId, String avatarUrl) {
        this.userName = name;
        this.studentId = studentId;
        this.avatarUrl = avatarUrl;
    }

    public String getUserName() {
        return userName;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}

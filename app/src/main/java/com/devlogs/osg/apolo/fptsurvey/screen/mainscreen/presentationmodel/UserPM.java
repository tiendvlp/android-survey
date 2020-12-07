package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel;

import java.io.Serializable;

public class UserPM implements Serializable {
    private String name;
    private String studentId;
    private String email;
    private String campus;
    private int admission;
    private String pictureUrl;

    public UserPM(String name, String studentId, String email, String campus, int admission, String pictureUrl) {
        this.name = name;
        this.studentId = studentId;
        this.email = email;
        this.campus = campus;
        this.admission = admission;
        this.pictureUrl = pictureUrl;
    }

    public String getCampus() {
        return campus;
    }

    public int getAdmission() {
        return admission;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getEmail() {
        return email;
    }

}

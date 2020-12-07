package com.devlogs.osg.apolo.fptsurvey.login;

import com.devlogs.osg.apolo.fptsurvey.domain.data.AuthDisk;
import com.devlogs.osg.apolo.fptsurvey.domain.data.UserDisk;

import javax.inject.Inject;

public class SignOutUseCase {
    private AuthDisk mAuthDisk;
    private UserDisk mUserDisk;

    @Inject
    public SignOutUseCase (AuthDisk authDisk, UserDisk userDisk) {
        mAuthDisk = authDisk;
        mUserDisk = userDisk;
    }

    public void execute () {
        mAuthDisk.clear();
        mUserDisk.clear();
    }
}

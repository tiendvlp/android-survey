package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.controller;

import com.devlogs.osg.apolo.fptsurvey.login.SignOutUseCase;

import javax.inject.Inject;

class SignOutController {
    private SignOutUseCase signOutUseCase;


    @Inject
    public SignOutController(SignOutUseCase signOutUseCase) {
        this.signOutUseCase = signOutUseCase;
    }

    public void signOut () {
        signOutUseCase.execute();
    }
}

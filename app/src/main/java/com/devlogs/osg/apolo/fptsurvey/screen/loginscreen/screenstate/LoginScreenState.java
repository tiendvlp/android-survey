package com.devlogs.osg.apolo.fptsurvey.screen.loginscreen.screenstate;

import com.devlogs.osg.apolo.fptsurvey.screen.common.basepresentationstate.PresentationState;

public class LoginScreenState implements PresentationState {
    private final String TAG = "LOGINSCREENSTATE";
    @Override
    public String getTag() {
        return TAG;
    }

    public static class LoggedInState extends LoginScreenState {}
    public static class LoadingState extends LoginScreenState {}
    public static class NotLoggedInState extends LoginScreenState {}
}

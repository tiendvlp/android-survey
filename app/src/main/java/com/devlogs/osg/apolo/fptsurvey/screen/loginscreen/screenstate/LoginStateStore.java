package com.devlogs.osg.apolo.fptsurvey.screen.loginscreen.screenstate;

import android.os.Bundle;
import android.util.Log;

import com.devlogs.osg.apolo.fptsurvey.screen.common.basepresentationstate.BasePresentationStateStore;
import com.devlogs.osg.apolo.fptsurvey.screen.common.basepresentationstate.PresentationState;

public class LoginStateStore extends BasePresentationStateStore<LoginScreenState> {
    public LoginStateStore () {
        super("LoginState");
        Log.d("LoginStateStore", "constructor");
    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
    }

    @Override
    public LoginScreenState restoreState() {
        LoginScreenState state = (LoginScreenState) super.restoreState();
        if (state == null) {
            state = (new LoginScreenState.NotLoggedInState());
        }
        updateState(state);
        return state;
    }

    @Override
    public void setState(LoginScreenState state) {
        if (LoginScreenState.LoadingState.class.equals(state.getClass())) {
            invokeListenerByState(state);
        }
        if (LoginScreenState.LoggedInState.class.equals(state.getClass())) {
            saveState(state);
            invokeListenerByState(state);
        }

        if (LoginScreenState.NotLoggedInState.class.equals(state.getClass())) {
            invokeListenerByState(state);
        }
    }

    @Override
    public void setEffect(LoginScreenState.PresentationEffect effect) {

    }
}

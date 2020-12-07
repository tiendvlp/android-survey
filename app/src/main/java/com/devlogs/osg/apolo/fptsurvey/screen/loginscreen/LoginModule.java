package com.devlogs.osg.apolo.fptsurvey.screen.loginscreen;

import com.devlogs.osg.apolo.fptsurvey.screen.loginscreen.screenstate.LoginStateStore;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.scopes.ActivityScoped;

@Module
@InstallIn(ActivityComponent.class)
public class LoginModule {
    @ActivityScoped
    @Provides
    public LoginStateStore provideLoginStateStore () {
        return new LoginStateStore();
    }
}

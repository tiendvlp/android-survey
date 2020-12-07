package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.common;

import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.screenstate.MainScreenPresentationStateStore;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.scopes.ActivityScoped;

@Module
@InstallIn(ActivityComponent.class)
public class MainScreenModule {
    @Provides
    @ActivityScoped
    public MainScreenPresentationStateStore provideMainStateStore () {
        return new MainScreenPresentationStateStore();
    }
}

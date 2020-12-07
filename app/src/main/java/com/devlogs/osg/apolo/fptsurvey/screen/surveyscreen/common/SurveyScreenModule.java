package com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.common;

import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.screenstate.MainScreenPresentationStateStore;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.screenstate.SurveyScreenPresentationState;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.screenstate.SurveyScreenPresentationStateStore;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.scopes.ActivityScoped;

@Module
@InstallIn(ActivityComponent.class)
public class SurveyScreenModule {

    @Provides
    @ActivityScoped
    public SurveyScreenPresentationStateStore provideSurveyScreenPresentationStateStore () {
        return new SurveyScreenPresentationStateStore();
    }
}

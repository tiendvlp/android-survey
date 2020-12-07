package com.devlogs.osg.apolo.fptsurvey.data.network.common;

import android.content.SharedPreferences;

import com.devlogs.osg.apolo.fptsurvey.data.disk.AuthDiskImp;
import com.devlogs.osg.apolo.fptsurvey.data.disk.UserDiskImp;
import com.devlogs.osg.apolo.fptsurvey.data.network.AuthNetworkImp;
import com.devlogs.osg.apolo.fptsurvey.data.network.QuickQuestionNetworkImp;
import com.devlogs.osg.apolo.fptsurvey.data.network.QuickQuestionResultNetworkImp;
import com.devlogs.osg.apolo.fptsurvey.data.network.StatusNetworkImp;
import com.devlogs.osg.apolo.fptsurvey.data.network.SurveyNetworkImp;
import com.devlogs.osg.apolo.fptsurvey.data.network.SurveyQuestionNetworkImp;
import com.devlogs.osg.apolo.fptsurvey.data.network.SurveyResultNetworkImp;
import com.devlogs.osg.apolo.fptsurvey.data.network.SurveyTopicNetworkImp;
import com.devlogs.osg.apolo.fptsurvey.data.network.UserNetworkImp;
import com.devlogs.osg.apolo.fptsurvey.domain.data.AuthDisk;
import com.devlogs.osg.apolo.fptsurvey.domain.data.AuthNetwork;
import com.devlogs.osg.apolo.fptsurvey.domain.data.QuickQuestionNetwork;
import com.devlogs.osg.apolo.fptsurvey.domain.data.QuickQuestionResultNetwork;
import com.devlogs.osg.apolo.fptsurvey.domain.data.StatusNetwork;
import com.devlogs.osg.apolo.fptsurvey.domain.data.SurveyNetwork;
import com.devlogs.osg.apolo.fptsurvey.domain.data.SurveyQuestionNetwork;
import com.devlogs.osg.apolo.fptsurvey.domain.data.SurveyResultNetwork;
import com.devlogs.osg.apolo.fptsurvey.domain.data.SurveyTopicNetwork;
import com.devlogs.osg.apolo.fptsurvey.domain.data.UserDisk;
import com.devlogs.osg.apolo.fptsurvey.domain.data.UserNetwork;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import retrofit2.Retrofit;

@Module
@InstallIn(ApplicationComponent.class)
public class DataModule {
    @Provides
    public SurveyNetwork provideSurveyNetwork (Retrofit retrofit) {
        return new SurveyNetworkImp(retrofit);
    }

    @Provides
    public SurveyQuestionNetwork provideSurveyQuestionNetwork (Retrofit retrofit) {
        return new SurveyQuestionNetworkImp(retrofit);
    }

    @Provides
    public UserNetwork provideUserNetwork (Retrofit retrofit) {
        return new UserNetworkImp(retrofit);
    }

    @Provides
    public AuthNetwork provideAuthNetwork (Retrofit retrofit) {
        return new AuthNetworkImp(retrofit);
    }

    @Provides
    public AuthDisk provideAuthDisk (SharedPreferences sharedPreferences) {
        return new AuthDiskImp(sharedPreferences);
    }

    @Provides
    SurveyTopicNetwork provideSurveyTopicNetwork (Retrofit retrofit) {
        return new SurveyTopicNetworkImp(retrofit);
    }

    @Provides
    UserDisk provideUserDisk (SharedPreferences sharedPreferences) {
        return new UserDiskImp(sharedPreferences);
    }

    @Provides
    StatusNetwork provideStatusNetwork (Retrofit retrofit) {
        return new StatusNetworkImp(retrofit);
    }

    @Provides
    QuickQuestionNetwork provideQuickQuestionNetwork (Retrofit retrofit) {
        return new QuickQuestionNetworkImp(retrofit);
    }

    @Provides
    QuickQuestionResultNetwork provideQuickQuestionResultNetwork (Retrofit retrofit) {
        return new QuickQuestionResultNetworkImp(retrofit);
    }

    @Provides
    SurveyResultNetwork provideSurveyResultNetwork (Retrofit retrofit) {
        return new SurveyResultNetworkImp(retrofit);
    }
}

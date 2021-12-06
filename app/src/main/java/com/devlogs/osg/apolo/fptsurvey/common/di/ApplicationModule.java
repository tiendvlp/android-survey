package com.devlogs.osg.apolo.fptsurvey.common.di;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.devlogs.osg.apolo.fptsurvey.common.AppSetting;
import com.devlogs.osg.apolo.fptsurvey.common.AppSettingManager;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@InstallIn(value = ApplicationComponent.class)
@Module
public class ApplicationModule {
    @Singleton
    @Provides
    public SharedPreferences provideSharedPreferences (Application context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Singleton
    @Provides
    public Retrofit provideRestfulApi () {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder().callTimeout(21, TimeUnit.SECONDS).connectTimeout(21, TimeUnit.SECONDS).build();

        return new Retrofit.Builder()
//        http://10.0.2.2:8080/api/v1/
// 172.20.10.4
                .baseUrl("https://surveyserverspringboot.herokuapp.com/api/v1/")
//                .baseUrl("http://10.0.2.2:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Singleton
    @Provides
    public AppSettingManager provideAppSettingManager (SharedPreferences sharedPreferences) {
        return new AppSettingManager(sharedPreferences);
    }
}

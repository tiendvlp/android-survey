package com.devlogs.osg.apolo.fptsurvey.common;

import android.content.SharedPreferences;

import com.google.gson.Gson;

public class AppSettingManager {
    private SharedPreferences sharedPreferences;

    public AppSettingManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        if (sharedPreferences.getString("AppSetting", null) == null)  {
            updateSetting(new AppSetting());
        }
    }

    public AppSetting getAppSetting () {
        return new Gson().fromJson(sharedPreferences.getString("AppSetting", null), AppSetting.class);
    }


    public void updateSetting (AppSetting appSetting) {
        this.sharedPreferences.edit().putString("AppSetting", new Gson().toJson(appSetting)).apply();
    }

    public void updateSetting (SettingCustomizer customizer) {
        AppSetting currentAppSetting = getAppSetting();
        AppSetting newAppSetting = customizer.custom(currentAppSetting);
        updateSetting(newAppSetting);
    }

    public interface SettingCustomizer {
         AppSetting custom (AppSetting currentSetting);
    }

}

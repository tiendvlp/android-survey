package com.devlogs.osg.apolo.fptsurvey.screen.launchscreen.controller;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.devlogs.osg.apolo.fptsurvey.screen.common.MvcViewFactory;
import com.devlogs.osg.apolo.fptsurvey.screen.launchscreen.mvc_view.LaunchScreenMvcView;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LaunchScreenActivity extends AppCompatActivity {
    @Inject
    public MvcViewFactory mMvcViewFactory;
    private LaunchScreenMvcView mMvcView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMvcView = mMvcViewFactory.getLaunchScreenMvcView(null);
        setContentView(mMvcView.getRootView());
    }
}

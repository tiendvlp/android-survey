package com.devlogs.osg.apolo.fptsurvey.screen.common;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;

import com.devlogs.osg.apolo.fptsurvey.screen.endscreen.mvcview.EndSurveyMvcView;
import com.devlogs.osg.apolo.fptsurvey.screen.endscreen.mvcview.EndSurveyMvcViewImp;
import com.devlogs.osg.apolo.fptsurvey.screen.launchscreen.mvc_view.LaunchScreenMvcView;
import com.devlogs.osg.apolo.fptsurvey.screen.launchscreen.mvc_view.LaunchScreenMvcViewImp;
import com.devlogs.osg.apolo.fptsurvey.screen.loginscreen.mvc_view.LoginMvcView;
import com.devlogs.osg.apolo.fptsurvey.screen.loginscreen.mvc_view.LoginMvcViewImp;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.mvc_view.MainMvcView;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.mvc_view.MainMvcViewImp;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.mvc_view.MainMvcViewV2Imp;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.mvcview.SurveyMvcView;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.mvcview.SurveyMvcViewImp;

public class MvcViewFactory {
    private LayoutInflater mLayoutInflater;

    public MvcViewFactory(LayoutInflater mLayoutInflater) {
        this.mLayoutInflater = mLayoutInflater;
    }

    public LaunchScreenMvcView getLaunchScreenMvcView (ViewGroup container) {
        return new LaunchScreenMvcViewImp(mLayoutInflater, container);
    }

    public LoginMvcView getLoginMvcView (ViewGroup container) {
        return new LoginMvcViewImp(mLayoutInflater, container);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public MainMvcView getMainMvcView (ViewGroup container) {
        return new MainMvcViewV2Imp(mLayoutInflater,container);
    }

    public SurveyMvcView getSurveyMvcView (ViewGroup container) {
        return new SurveyMvcViewImp(mLayoutInflater, container);
    }

    public EndSurveyMvcView getEndSurveyMvcView (ViewGroup container) {
        return new EndSurveyMvcViewImp(mLayoutInflater, container);
    }
}

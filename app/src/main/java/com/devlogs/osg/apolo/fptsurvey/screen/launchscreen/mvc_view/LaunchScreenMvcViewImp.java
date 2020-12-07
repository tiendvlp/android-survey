package com.devlogs.osg.apolo.fptsurvey.screen.launchscreen.mvc_view;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.devlogs.osg.apolo.fptsurvey.R;
import com.devlogs.osg.apolo.fptsurvey.screen.common.basemvcview.BaseMvcView;

public class LaunchScreenMvcViewImp extends BaseMvcView<LaunchScreenMvcView.Listener> implements LaunchScreenMvcView {
    public LaunchScreenMvcViewImp (LayoutInflater layoutInflater, ViewGroup container) {
        setRootView(layoutInflater.inflate(R.layout.layout_launchscreen, container, false));
    }

    @Override
    public void show() {

    }
}

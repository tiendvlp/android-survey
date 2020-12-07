package com.devlogs.osg.apolo.fptsurvey.screen.launchscreen.mvc_view;
import com.devlogs.osg.apolo.fptsurvey.common.base.Observable;
import com.devlogs.osg.apolo.fptsurvey.screen.common.basemvcview.MvcView;

public interface LaunchScreenMvcView extends Observable<LaunchScreenMvcView.Listener>, MvcView {
    interface Listener {
    }

    void show ();
}

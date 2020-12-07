package com.devlogs.osg.apolo.fptsurvey.screen.endscreen.mvcview;


import com.devlogs.osg.apolo.fptsurvey.common.base.Observable;
import com.devlogs.osg.apolo.fptsurvey.screen.common.basemvcview.MvcView;

public interface EndSurveyMvcView extends Observable<EndSurveyMvcView.Listener>, MvcView {
    public interface Listener {

    }

    public void drawUI (String layout, String data);
}

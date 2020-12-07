package com.devlogs.osg.apolo.fptsurvey.screen.common.toolbar;

import com.devlogs.osg.apolo.fptsurvey.common.base.Observable;
import com.devlogs.osg.apolo.fptsurvey.screen.common.basemvcview.MvcView;

public interface HeaderToolbarMvcView extends Observable<HeaderToolbarMvcView.Listener>, MvcView {
    interface Listener {}

    void setHeader (String header);
    void headerFadeIn ();
    void headerFadeOut();
    void divideFadeIn ();
    void divideFadeOut ();
}

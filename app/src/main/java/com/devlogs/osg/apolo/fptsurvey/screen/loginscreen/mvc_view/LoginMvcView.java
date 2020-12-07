package com.devlogs.osg.apolo.fptsurvey.screen.loginscreen.mvc_view;

import com.devlogs.osg.apolo.fptsurvey.common.base.Observable;
import com.devlogs.osg.apolo.fptsurvey.screen.common.basemvcview.MvcView;

public interface LoginMvcView extends Observable<LoginMvcView.Listener>, MvcView {
    interface Listener {
        void onBtnLoginWithGGClicked ();
    }

    void hideLoadingView ();
    void showLoadingView ();
    void pauseAnim ();
    void resumeAnim ();
}

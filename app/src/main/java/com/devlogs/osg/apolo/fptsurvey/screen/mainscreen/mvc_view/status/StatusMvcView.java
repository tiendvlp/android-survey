package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.mvc_view.status;

import com.devlogs.osg.apolo.fptsurvey.screen.common.basemvcview.ObservableMvcView;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.StatusPM;

import java.util.List;

public interface StatusMvcView extends ObservableMvcView<StatusMvcView.Listener> {
    interface Listener {

    }

    public void showStatus (List<StatusPM> bunchOfStatusPM);
}

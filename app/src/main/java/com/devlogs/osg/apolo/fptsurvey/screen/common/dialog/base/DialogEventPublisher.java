package com.devlogs.osg.apolo.fptsurvey.screen.common.dialog.base;

import com.devlogs.osg.apolo.fptsurvey.common.base.BaseObservable;

public class DialogEventPublisher extends BaseObservable<DialogEventPublisher.Listener> {
    public interface Listener {
        void onDialog (Object any);
    }

    public void publish (Object any) {
        for (Listener listener : getListeners()) {
            listener.onDialog(any);
        }
    }
}

package com.devlogs.osg.apolo.fptsurvey.screen.common.basepresentationstate;

import com.devlogs.osg.apolo.fptsurvey.common.base.BaseObservable;

public class BasePresentationStateObservable extends BaseObservable<PresentationStateListener> {

    protected void invokeListenerByState (PresentationState state) {
        for (PresentationStateListener listener : getListeners()) {
            listener.onStateChanged(state);
        }
    }

    protected void invokeListenerByEffect (PresentationState.PresentationEffect effect) {
        for (PresentationStateListener listener : getListeners()) {
            listener.onEffectOccur(effect);
            listener.onEffectOccur(null);
        }
    }

}

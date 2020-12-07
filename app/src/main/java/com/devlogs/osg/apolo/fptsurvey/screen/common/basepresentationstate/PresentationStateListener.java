package com.devlogs.osg.apolo.fptsurvey.screen.common.basepresentationstate;

public interface PresentationStateListener {
    void onStateChanged (PresentationState state);
    void onEffectOccur (PresentationState.PresentationEffect effect);
}

package com.devlogs.osg.apolo.fptsurvey.screen.common.basemvcview;

import java.util.HashSet;
import java.util.Set;

public abstract class BaseObservableMvcView<LISTENER>  implements ObservableMvcView<LISTENER> {
    private HashSet<LISTENER> listeners = new HashSet();

    public Set<LISTENER> getListeners () {
        return listeners;
    }

    @Override
    public void register(LISTENER listener) {
        listeners.add(listener);
    }

    @Override
    public void unRegister(LISTENER listener) {
        listeners.remove(listener);
    }
}

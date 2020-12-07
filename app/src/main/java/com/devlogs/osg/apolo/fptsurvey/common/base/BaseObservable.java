package com.devlogs.osg.apolo.fptsurvey.common.base;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class BaseObservable<LISTENER> implements Observable<LISTENER> {

    private final Object MONITOR = new Object();

    private final Set<LISTENER> mListeners = new HashSet<>();

    public void register(LISTENER listener) {
        synchronized (MONITOR) {
            boolean hadNoListeners = mListeners.size() == 0;
            mListeners.add(listener);
            if (hadNoListeners && mListeners.size() == 1) {
                onFirstListenerRegistered();
            }
        }
    }

    public void unRegister(LISTENER listener) {
        synchronized (MONITOR) {
            boolean hadOneListener = mListeners.size() == 1;
            mListeners.remove(listener);
            if (hadOneListener && mListeners.size() == 0) {
                onLastListenerUnregistered();
            }
        }
    }

    protected Set<LISTENER> getListeners() {
        synchronized (MONITOR) {
            return Collections.unmodifiableSet(new HashSet<>(mListeners));
        }
    }

    protected void onFirstListenerRegistered() {

    }

    protected void onLastListenerUnregistered() {

    }

}
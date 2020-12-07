package com.devlogs.osg.apolo.fptsurvey.screen.common.basepresentationstate;

import android.os.Bundle;

public abstract class BasePresentationStateStore<T extends PresentationState> extends BasePresentationStateObservable {
    protected Bundle mainInstanceState;
    protected final String savedStateKey;
    private T currentState;

    public BasePresentationStateStore (String savedStateKey) {
        this.savedStateKey = savedStateKey;
        mainInstanceState = new Bundle();
    }

    public void init (Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(savedStateKey)) {
            mainInstanceState = savedInstanceState.getBundle(savedStateKey);
        }
    }

    public T getCurrentState() {
        return currentState;
    }

    public T restoreState () {
        if (mainInstanceState.containsKey(savedStateKey)) {
            return (T) mainInstanceState.get(savedStateKey);
        }
        return null;
    }

    public void onSavedInstanceState (Bundle outState) {
        outState.putBundle(savedStateKey, mainInstanceState);
    }

    protected void saveState (T state) {
        mainInstanceState.putSerializable(savedStateKey, state);
    }

    public final void updateState (T state) {
        setState(state);
        this.currentState = state;
    }

    public final void updateEffect (T.PresentationEffect effect) {
        setEffect(effect);
    }

    protected abstract void setState (T state);
    protected abstract void setEffect (T.PresentationEffect effect);
}

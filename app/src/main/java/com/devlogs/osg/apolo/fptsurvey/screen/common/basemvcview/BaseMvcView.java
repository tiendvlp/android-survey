package com.devlogs.osg.apolo.fptsurvey.screen.common.basemvcview;

import android.content.Context;
import android.view.View;

import com.devlogs.osg.apolo.fptsurvey.common.base.BaseObservable;

public abstract class BaseMvcView<Listener> extends BaseObservable<Listener> implements MvcView {
    private View mRootView;

    public void setRootView (View rootView) {
        mRootView = rootView;
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    public Context getContext () {
        return getRootView().getContext();
    }

    public <TYPE extends View> TYPE findViewById (int id) {
        return getRootView().findViewById(id);
    }
}

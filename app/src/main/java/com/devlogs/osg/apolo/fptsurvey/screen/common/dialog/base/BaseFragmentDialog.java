package com.devlogs.osg.apolo.fptsurvey.screen.common.dialog.base;

import androidx.fragment.app.DialogFragment;

import com.devlogs.osg.apolo.fptsurvey.common.base.Observable;


public class BaseFragmentDialog extends DialogFragment implements Observable<DialogEventPublisher.Listener> {

    private DialogEventPublisher mPublisher;

    public BaseFragmentDialog () {
        mPublisher = new DialogEventPublisher();
    }

    protected DialogEventPublisher getEventPublisher () {
        return mPublisher;
    }


    @Override
    public void register(DialogEventPublisher.Listener listener) {
        mPublisher.register(listener);
    }

    @Override
    public void unRegister(DialogEventPublisher.Listener listener) {
        mPublisher.unRegister(listener);
    }
}

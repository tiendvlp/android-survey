package com.devlogs.osg.apolo.fptsurvey.common.base;

public interface Observable<LISTENER> {
    void register (LISTENER listener);
    void unRegister (LISTENER listener);
}

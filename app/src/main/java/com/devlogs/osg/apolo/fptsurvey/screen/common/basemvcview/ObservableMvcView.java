package com.devlogs.osg.apolo.fptsurvey.screen.common.basemvcview;

public interface ObservableMvcView<LISTENER> extends MvcView{
    void register (LISTENER listener);
    void unRegister (LISTENER listener);
}

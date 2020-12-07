package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.mvc_view;

import com.devlogs.osg.apolo.fptsurvey.common.base.Observable;
import com.devlogs.osg.apolo.fptsurvey.screen.common.basemvcview.MvcView;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.SurveyPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.SurveyTopicPM;

import java.util.List;

public interface TopicItemMvcView extends Observable<TopicItemMvcView.Listener>, MvcView {
    interface Listener {
        void onItemClickedListener (SurveyPM surveyPM);
    }

    void updateSurvey (List<SurveyPM> surveys);
    void init (SurveyTopicPM surveyTopic);
}

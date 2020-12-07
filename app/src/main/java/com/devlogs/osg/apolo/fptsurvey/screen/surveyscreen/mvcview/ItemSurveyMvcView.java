package com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.mvcview;

import com.devlogs.osg.apolo.fptsurvey.common.base.Observable;
import com.devlogs.osg.apolo.fptsurvey.screen.common.basemvcview.MvcView;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.presentationmodel.QuestionPM;

public interface ItemSurveyMvcView extends Observable<ItemSurveyMvcView.Listener>, MvcView {
    interface Listener {
        void onAnswerChecked (QuestionPM question, int answer) ;
    }

    void showQuestion (QuestionPM question);
}

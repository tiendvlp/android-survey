package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.mvc_view;


import com.devlogs.osg.apolo.fptsurvey.common.base.Observable;
import com.devlogs.osg.apolo.fptsurvey.screen.common.basemvcview.MvcView;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.QuickQuestionPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.StatusPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.SurveyPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.SurveyTopicPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.UserPM;

import java.util.List;

public interface MainMvcView extends Observable<MainMvcView.Listener>, MvcView {
    interface Listener {
        void onSurveyClicked (SurveyPM surveyPM);
        void onBtnRetryClicked ();
        void onBtnUserInfoClicked ();
        void onRefreshLayout ();
        void onBtnQuickQuestionSubmitClicked (String questionId, int selectedId);
    }

    void showLoadingView (boolean showMain);
    void hideLoadingView();
    void showQuickQuestion (List<QuickQuestionPM> bunchOfQuickQuestionPM);
    void showStatus (List<StatusPM> bunchOfStatusPm);
    void showSurveys(List<SurveyPM> surveys, List<SurveyTopicPM> topics, UserPM userPM);
    void showError (String errorMessage);
    void hideQuickQuestion (String questionId);
}

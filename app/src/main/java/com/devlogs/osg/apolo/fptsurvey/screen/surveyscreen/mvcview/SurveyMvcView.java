package com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.mvcview;

import com.devlogs.osg.apolo.fptsurvey.common.base.Observable;
import com.devlogs.osg.apolo.fptsurvey.screen.common.basemvcview.MvcView;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.presentationmodel.QuestionPM;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.presentationmodel.SurveyPM;

import java.util.List;

public interface SurveyMvcView extends Observable<SurveyMvcView.Listener>, MvcView {
    interface Listener {
        void onUserSelectAnswer (int question, int answer);
        void onBtnRetryClicked ();
        void onBtnSubmitClicked ();
    }

    void showSurvey (SurveyPM survey);
    void showLoadingLayout ();
    void showError (String errorMessage);
    void updateSubmitCount (int answeredQuestion);
    void showQuestions (List<QuestionPM> questionPMS);
}

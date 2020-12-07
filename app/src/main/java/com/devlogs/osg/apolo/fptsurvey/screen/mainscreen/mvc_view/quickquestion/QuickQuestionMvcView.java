package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.mvc_view.quickquestion;

import com.devlogs.osg.apolo.fptsurvey.screen.common.basemvcview.ObservableMvcView;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.QuickQuestionPM;

import java.util.List;

public interface QuickQuestionMvcView extends ObservableMvcView<QuickQuestionMvcView.Listener> {
    interface Listener {
        void onQuickQuestionBtnSubmitClicked (String questionId, int selectedAnswer);
    }
    void showQuickQuestions (List<QuickQuestionPM> bunchOfQuickQuestionPm);
    void hideQuickQuestion (String questionId);
}

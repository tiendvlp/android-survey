package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.screenstate;

import android.util.Log;

import com.devlogs.osg.apolo.fptsurvey.screen.common.basepresentationstate.BasePresentationStateStore;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.controller.GetQuickQuestionController;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.controller.GetStatusController;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.SurveyPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.SurveyTopicPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.UserPM;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainScreenPresentationStateStore extends BasePresentationStateStore<MainScreenPresentationState> {
    @Inject
    public MainScreenPresentationStateStore() {
        super("SurveyPresentationStateStore");
    }

    @Override
    protected void setState(MainScreenPresentationState state) {
        if (MainScreenPresentationState.Loading.class.equals(state.getClass())) {
            invokeListenerByState(state);
        }
        if (MainScreenPresentationState.Error.class.equals(state.getClass())) {
            saveState(state);
            invokeListenerByState(state);
        }
        if (MainScreenPresentationState.FinishedState.class.equals(state.getClass())) {
            saveState(state);
            invokeListenerByState(state);
        }

    }


    private List<SurveyPM> surveys = new ArrayList();
    private List<SurveyTopicPM> topics = new ArrayList();
    private UserPM user = null;

    @Override
    protected void setEffect(MainScreenPresentationState.PresentationEffect effect) {
        if (MainScreenPresentationState.MainScreenPresentationEffect.UserLoaded.class.equals(effect.getClass())) {
            invokeListenerByEffect(effect);
            user = ((MainScreenPresentationState.MainScreenPresentationEffect.UserLoaded) effect).userPM;
            if (!surveys.isEmpty() && !topics.isEmpty()) {
                Log.d("MainPMStore", "User loaded");
                updateState(new MainScreenPresentationState.FinishedState(surveys, topics, user));

            }
        }

        if (MainScreenPresentationState.MainScreenPresentationEffect.SurveyLoaded.class.equals(effect.getClass())) {
            invokeListenerByEffect(effect);
            surveys.clear();
            topics.clear();
            surveys.addAll(((MainScreenPresentationState.MainScreenPresentationEffect.SurveyLoaded) effect).surveyPMS);
            topics.addAll(((MainScreenPresentationState.MainScreenPresentationEffect.SurveyLoaded) effect).surveyTopicPMS);

            if (user != null) {
                Log.d("MainPMStore", "Survey Loaded");
                updateState(new MainScreenPresentationState.FinishedState(surveys, topics, user));
            }
        }

        if (MainScreenPresentationState.MainScreenPresentationEffect.StatusLoaded.class.equals(effect.getClass())) {
            invokeListenerByEffect(effect);
        }

        if (MainScreenPresentationState.MainScreenPresentationEffect.QuickQuestionLoaded.class.equals(effect.getClass())) {
            invokeListenerByEffect(effect);
        }
    }
}

package com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.screenstate;
import com.devlogs.osg.apolo.fptsurvey.screen.common.basepresentationstate.BasePresentationStateStore;

public class SurveyScreenPresentationStateStore extends BasePresentationStateStore<SurveyScreenPresentationState> {

    public SurveyScreenPresentationStateStore() {
        super("SurveyScreenPresentationStateStore");
    }

    @Override
    protected void setState(SurveyScreenPresentationState state) {
        if (SurveyScreenPresentationState.Error.class.equals(state.getClass())) {
            saveState(state);
            invokeListenerByState(state);
        }
        if (SurveyScreenPresentationState.Loading.class.equals(state.getClass())) {
            saveState(state);
            invokeListenerByState(state);
        }
        if (SurveyScreenPresentationState.Finished.class.equals(state.getClass())) {
            saveState(state);
            invokeListenerByState(state);
        }
    }

    public void updateSelectedAnswer (int question, int answer) {
        SurveyScreenPresentationState.Finished finishedState = (SurveyScreenPresentationState.Finished) getCurrentState();
        finishedState.question.get(question).setCheckedAnswer(answer);
        saveState(finishedState);
    }

    public void updateAnsweredQuestionCount (int count) {
        SurveyScreenPresentationState.Finished finishedState = (SurveyScreenPresentationState.Finished) getCurrentState();
        finishedState.getSurveyPM().setNumberOfCheckedAnswer(count);
        saveState(finishedState);
    }

    @Override
    protected void setEffect(SurveyScreenPresentationState.PresentationEffect effect) {
        if (SurveyScreenPresentationState.Effect.SubmitResultDone.class.equals(effect.getClass())) {
            invokeListenerByEffect(effect);
        }
    }
}

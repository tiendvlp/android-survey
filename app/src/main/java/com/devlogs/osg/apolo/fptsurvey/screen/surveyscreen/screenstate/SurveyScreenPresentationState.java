package com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.screenstate;

import com.devlogs.osg.apolo.fptsurvey.screen.common.basepresentationstate.PresentationState;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.presentationmodel.QuestionPM;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.presentationmodel.SurveyPM;

import java.util.List;

public class SurveyScreenPresentationState implements PresentationState {
    @Override
    public String getTag() {
        return "SurveyScreenPresentationState";
    }

    private SurveyPM surveyPM;

    public SurveyScreenPresentationState(SurveyPM surveyPM) {
        this.surveyPM = surveyPM;
    }

    public SurveyPM getSurveyPM() {
        return surveyPM;
    }

    public static class Loading extends SurveyScreenPresentationState {
        public Loading(SurveyPM surveyPM) {
            super(surveyPM);
        }
    }

    public static class Error extends SurveyScreenPresentationState {
        public Throwable error;

        public Error(Throwable error, SurveyPM surveyPM) {
            super(surveyPM);
            this.error = error;
        }
    }
    public static class Finished extends SurveyScreenPresentationState {
        public List<QuestionPM> question;
        public Finished(SurveyPM surveyPM, List<QuestionPM> questionPM) {
            super(surveyPM);
            this.question = questionPM;
        }
    }

    public static class Effect implements SurveyScreenPresentationState.PresentationEffect {
        public static class SubmitResultDone extends SurveyScreenPresentationState.Effect {
            String surveyResultId = "";

            public SubmitResultDone(String surveyResultId) {
                this.surveyResultId = surveyResultId;
            }

            public String getSurveyResultId() {
                return surveyResultId;
            }

            public void setSurveyResultId(String surveyResultId) {
                this.surveyResultId = surveyResultId;
            }
        };
    }

}

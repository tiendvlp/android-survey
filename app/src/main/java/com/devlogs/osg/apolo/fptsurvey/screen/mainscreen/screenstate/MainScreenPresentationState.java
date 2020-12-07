package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.screenstate;

import com.devlogs.osg.apolo.fptsurvey.screen.common.basepresentationstate.PresentationState;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.QuickQuestionPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.StatusPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.SurveyPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.SurveyTopicPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.UserPM;

import java.util.List;

public class MainScreenPresentationState implements PresentationState {
    @Override
    public String getTag() {
        return "SurveyScreenPresentationState";
    }

    public static class Loading extends MainScreenPresentationState {
    };

    public static class Error extends MainScreenPresentationState {
        public String errorMessage;

        public Error(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    };

    public static class FinishedState extends MainScreenPresentationState {
        public List<SurveyPM> surveyPMS;
        public List<SurveyTopicPM> surveyTopicPMS;
        public UserPM userPM;

        public FinishedState(List<SurveyPM> surveyPMS, List<SurveyTopicPM> surveyTopicPMS, UserPM userPM) {
            this.surveyPMS = surveyPMS;
            this.userPM = userPM;
            this.surveyTopicPMS = surveyTopicPMS;
        }
    }

    public static class MainScreenPresentationEffect implements MainScreenPresentationState.PresentationEffect {

        public static class SurveyLoaded extends MainScreenPresentationEffect {
            public List<SurveyPM> surveyPMS;
            public List<SurveyTopicPM> surveyTopicPMS;

            public SurveyLoaded(List<SurveyPM> surveyPMS, List<SurveyTopicPM> surveyTopicPMS) {
                this.surveyPMS = surveyPMS;
                this.surveyTopicPMS = surveyTopicPMS;
            }
        }

        public static class QuickQuestionLoaded implements MainScreenPresentationState.PresentationEffect {
            public List<QuickQuestionPM> quickQuestionPMS;

            public QuickQuestionLoaded(List<QuickQuestionPM> quickQuestionPMS) {
                this.quickQuestionPMS = quickQuestionPMS;
            }
        }

        public static class StatusLoaded implements MainScreenPresentationState.PresentationEffect {
            public List<StatusPM> statusPMS;

            public StatusLoaded(List<StatusPM> statusPMS) {
                this.statusPMS = statusPMS;
            }
        }

        public static class UserLoaded extends MainScreenPresentationEffect {
            public UserPM userPM;

            public UserLoaded(UserPM userPM) {
                this.userPM = userPM;
            }
        }
    }
}

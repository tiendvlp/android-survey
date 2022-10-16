package com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.devlogs.osg.apolo.fptsurvey.screen.common.MvcViewFactory;
import com.devlogs.osg.apolo.fptsurvey.screen.common.basepresentationstate.PresentationState;
import com.devlogs.osg.apolo.fptsurvey.screen.common.basepresentationstate.PresentationStateListener;
import com.devlogs.osg.apolo.fptsurvey.screen.endscreen.controller.EndActivity;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.mvcview.SurveyMvcView;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.presentationmodel.QuestionPM;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.presentationmodel.SurveyPM;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.screenstate.SurveyScreenPresentationState;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.screenstate.SurveyScreenPresentationStateStore;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public
class SurveyActivity extends AppCompatActivity implements PresentationStateListener, SurveyMvcView.Listener {
    @Inject
    public MvcViewFactory mvcViewFactory;
    @Inject
    public SurveyScreenPresentationStateStore stateStore;
    @Inject
    public GetQuestionController getQuestionController;
    @Inject
    public SubmitAnswerController submitAnswerController;
    private SurveyMvcView mView;
    private List<Integer> selectedAnswer = new ArrayList<>();
    String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = mvcViewFactory.getSurveyMvcView(null);
        setContentView(mView.getRootView());
        stateStore.init(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        stateStore.onSavedInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        stateStore.register(this);
        mView.register(this);
        restoreState();
    }

    private void restoreState() {
        SurveyScreenPresentationState state = stateStore.restoreState();
        if (state == null) {
            String id = getIntent().getStringExtra("surveyId");
            this.name = getIntent().getStringExtra("surveyName");

            Log.d("SurveyActivity", name);

            if (id == null || name == null || id.isEmpty() || name.isEmpty()) {
                finish();
            }
            state = new SurveyScreenPresentationState.Loading(new SurveyPM(id, name));
        }
        stateStore.updateState(state);
    }

    @Override
    protected void onStop() {
        super.onStop();
        stateStore.unRegister(this);
        mView.unRegister(this);
    }

    @Override
    public void onStateChanged(PresentationState state) {
        if (SurveyScreenPresentationState.Error.class.equals(state.getClass())) {
            mView.showError(((SurveyScreenPresentationState.Error) state).error.getMessage());
        }
        if (SurveyScreenPresentationState.Loading.class.equals(state.getClass())) {
            mView.showLoadingLayout();
            Log.d("SurveyScreenStore", "Loading");
            getQuestionController.getAllQuestion(((SurveyScreenPresentationState.Loading) state).getSurveyPM().getSurveyId());
        }
        if (SurveyScreenPresentationState.Finished.class.equals(state.getClass())) {
            Log.d("SurveyActivity", ((SurveyScreenPresentationState.Finished) state).question.size() + "");
            mView.showSurvey(((SurveyScreenPresentationState.Finished) state).getSurveyPM());
            mView.showQuestions(((SurveyScreenPresentationState.Finished) state).question);
        }
    }

    @Override
    public void onEffectOccur(PresentationState.PresentationEffect effect) {
        if (effect == null) {return;}
        if (SurveyScreenPresentationState.Effect.SubmitResultDone.class.equals(effect.getClass())) {
            Log.d("SurveyResultId", ((SurveyScreenPresentationState.Effect.SubmitResultDone) effect).getSurveyResultId());
            EndActivity.start(((SurveyScreenPresentationState.Effect.SubmitResultDone) effect).getSurveyResultId(), name,this);
            finish();
        }
    }

    public static void start(Context context, String surveyName, String surveyId) {
        Intent intent = new Intent(context, SurveyActivity.class);
        intent.putExtra("surveyName", surveyName);
        intent.putExtra("surveyId", surveyId);
        context.startActivity(intent);
    }

    @Override
    public void onUserSelectAnswer(int question, int answer) {
        Log.d("SurveyActivity", "question: " + question + ", answer: " + answer);

        stateStore.updateSelectedAnswer(question, answer);
        SurveyScreenPresentationState.Finished currentState = (SurveyScreenPresentationState.Finished) stateStore.getCurrentState();
        int questionCount = 0;
        for (QuestionPM ques : currentState.question) {
            if (ques.getCheckedAnswer() != -1) {
                ++questionCount;
            }
        }
        stateStore.updateAnsweredQuestionCount(questionCount);
        mView.updateSubmitCount(questionCount);
    }

    @Override
    public void onBtnRetryClicked() {
        if (stateStore.getCurrentState().getClass().equals(SurveyScreenPresentationState.Error.class)) {
            submitAnswerController.submitAnswer(stateStore.getCurrentState().getSurveyPM().getSurveyId(), selectedAnswer);
        } else {
            stateStore.updateState(new SurveyScreenPresentationState.Loading(stateStore.getCurrentState().getSurveyPM()));
        }
    }

    @Override
    public void onBtnSubmitClicked() {
        if (stateStore.getCurrentState() instanceof SurveyScreenPresentationState.Finished) {
            selectedAnswer.clear();
            List<QuestionPM> questions = ((SurveyScreenPresentationState.Finished) stateStore.getCurrentState()).question;
            for (QuestionPM question : questions) {
                if (question.getCheckedAnswer() == -1) {
                    return;
                }
                selectedAnswer.add(question.getCheckedAnswer());
            }
            submitAnswerController.submitAnswer(stateStore.getCurrentState().getSurveyPM().getSurveyId(), selectedAnswer);
        }
    }
}

package com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.mvcview;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devlogs.osg.apolo.fptsurvey.R;
import com.devlogs.osg.apolo.fptsurvey.screen.common.animation.RecyclerViewBounceEffectWrapper;
import com.devlogs.osg.apolo.fptsurvey.screen.common.basemvcview.BaseMvcView;
import com.devlogs.osg.apolo.fptsurvey.screen.common.toolbar.HeaderToolbarMvcView;
import com.devlogs.osg.apolo.fptsurvey.screen.common.toolbar.HeaderToolbarMvcViewImp;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.controller.QuestionRcvAdapter;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.presentationmodel.QuestionPM;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.presentationmodel.SurveyPM;

import java.util.ArrayList;
import java.util.List;


public class SurveyMvcViewImp extends BaseMvcView<SurveyMvcView.Listener> implements SurveyMvcView, QuestionRcvAdapter.Listener {
    private RecyclerView rcvSurvey;
    private Toolbar mToolbar;
    private QuestionRcvAdapter mRcvAdapter;
    private Button btnSubmit;
    private LinearLayout loadingLayout;
    private LinearLayout errorLayout;
    private ConstraintLayout surveyLayout;
    private HeaderToolbarMvcView mToolbarMvcView;
    private SurveyPM mSurveyPM;
    private Button btnRetry;
    private ArrayList questions = new ArrayList();

    public SurveyMvcViewImp(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.layout_survey, container, false));
        rcvSurvey = findViewById(R.id.rcvSurvey);
        mToolbar = findViewById(R.id.toolbar);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnRetry = findViewById(R.id.btnRetry);
        loadingLayout = findViewById(R.id.layoutLoading);
        errorLayout = findViewById(R.id.layoutError);
        surveyLayout = findViewById(R.id.layoutSurvey);
        mToolbarMvcView = new HeaderToolbarMvcViewImp(inflater, mToolbar);
        mToolbar.addView(mToolbarMvcView.getRootView());
        mToolbar.bringToFront();
        mToolbarMvcView.getRootView().bringToFront();
        addEvent();
    }

    private void addEvent () {
        btnRetry.setOnClickListener(view -> {
            for (Listener listener : getListeners()) {
                listener.onBtnRetryClicked();
            }
        });

        btnSubmit.setOnClickListener(view -> {
            for (Listener listener : getListeners()) {
                listener.onBtnSubmitClicked();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initAdapter () {
        mRcvAdapter = new QuestionRcvAdapter(mSurveyPM, questions);
        rcvSurvey.setAdapter(mRcvAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcvSurvey.setLayoutManager(manager);
        mRcvAdapter.register(this);
        new RecyclerViewBounceEffectWrapper().activateEffect(rcvSurvey);
        rcvSurvey.setOnScrollChangeListener(new View.OnScrollChangeListener() {

            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {

                if (manager.findFirstCompletelyVisibleItemPosition() != 0) {
                    mToolbarMvcView.divideFadeIn();
                } else {
                    mToolbarMvcView.divideFadeOut();
                }

                if (manager.findFirstVisibleItemPosition() ==  0) {
                    mToolbarMvcView.headerFadeOut();
                } else {
                    mToolbarMvcView.headerFadeIn();
                };
            }
        });
    }

    private void toolbarFadeOut () {
            mToolbar.animate().alpha(0f).setDuration(80);
    }

    private void toolbarFadeIn () {
            mToolbar.animate().alpha(1f).setDuration(80);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void showSurvey(SurveyPM survey) {
        this.mSurveyPM = survey;
        this.btnSubmit.setEnabled(false);
        this.surveyLayout.setVisibility(View.VISIBLE);
        this.errorLayout.setVisibility(View.GONE);
        this.loadingLayout.setVisibility(View.GONE);
        if (mRcvAdapter == null) {
            initAdapter();
        }
        mRcvAdapter.notifyDataSetChanged();
        mToolbarMvcView.setHeader(survey.getSurveyName());
    }

    @Override
    public void showLoadingLayout() {
        this.surveyLayout.setVisibility(View.GONE);
        this.errorLayout.setVisibility(View.GONE);
        this.loadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String errorMessage) {
        this.surveyLayout.setVisibility(View.GONE);
        this.errorLayout.setVisibility(View.VISIBLE);
        this.loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void updateSubmitCount(int answeredQuestion) {
        btnSubmit.setText(String.format("Finished: %d/%d\t\t\t\tSEND US", answeredQuestion, questions.size()));
        if (answeredQuestion == questions.size()) {
            btnSubmit.setEnabled(true);
        } else {
            btnSubmit.setEnabled(false);
        }
    }

    @Override
    public void showQuestions(List<QuestionPM> questionPMS) {
        this.questions.clear();
        this.questions.addAll(questionPMS);
        btnSubmit.setText(String.format("Finished: %d/%d\t\t\t\tSEND US", mSurveyPM.getNumberOfCheckedAnswer(), questions.size()));
        mRcvAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(QuestionRcvAdapter.UserSelection userSelection) {
        for (Listener listener : getListeners()) {
            listener.onUserSelectAnswer(userSelection.question, userSelection.answer);
        }
    }
}

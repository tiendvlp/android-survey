package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.mvc_view;

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
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.controller.SurveyTopicRcvAdapter;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.QuickQuestionPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.StatusPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.SurveyPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.SurveyTopicPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.UserPM;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.exceptions.OnErrorNotImplementedException;


public class MainMvcViewImp extends BaseMvcView<MainMvcView.Listener> implements MainMvcView {
    private RecyclerView rcvTopic;
    private LinearLayout layoutLoading;
    private LinearLayout layoutError;
    private ConstraintLayout layoutMain;
    private Button btnRetry;
    private SurveyTopicRcvAdapter mAdapter;
    private ArrayList<SurveyTopicPM> surveyTopics = new ArrayList();
    private ArrayList<SurveyPM> surveys = new ArrayList();
    private Toolbar mToolbar;
    private HeaderToolbarMvcView mToolbarMvcView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public MainMvcViewImp (LayoutInflater layoutInflater, ViewGroup container) {
        setRootView(layoutInflater.inflate(R.layout.layout_main, container, false));
        mToolbarMvcView = new HeaderToolbarMvcViewImp(layoutInflater, mToolbar);
        addControls();
        mToolbar.addView(mToolbarMvcView.getRootView());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        layoutManager.setSmoothScrollbarEnabled(true);
        rcvTopic.setLayoutManager(layoutManager);
        mToolbar.bringToFront();
        mToolbarMvcView.getRootView().bringToFront();
        addEvents();
    }

    private void addControls () {
        rcvTopic = findViewById(R.id.rcvTopic);
        mToolbar = findViewById(R.id.toolbar);
        mAdapter = new SurveyTopicRcvAdapter();
        layoutLoading = findViewById(R.id.layoutLoading);
        layoutMain = findViewById(R.id.layoutMain);
        layoutError = findViewById(R.id.layoutError);
        btnRetry = findViewById(R.id.btnRetry);
        new RecyclerViewBounceEffectWrapper().activateEffect(rcvTopic);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void addEvents () {
        rcvTopic.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if (((LinearLayoutManager)rcvTopic.getLayoutManager()).findFirstCompletelyVisibleItemPosition() != 0) {
                    mToolbarMvcView.divideFadeIn();
                } else {
                    mToolbarMvcView.divideFadeOut();
                }

                if (((LinearLayoutManager)rcvTopic.getLayoutManager()).findFirstVisibleItemPosition() ==  0) {
                    mToolbarMvcView.headerFadeOut();
                } else {
                    mToolbarMvcView.headerFadeIn();
                };
            }
        });

        mAdapter.register(new SurveyTopicRcvAdapter.Listener() {
            @Override
            public void onItemClicked(SurveyPM surveyPM) {
                for (Listener listener: getListeners()) {
                    listener.onSurveyClicked(surveyPM);
                }
            }

            @Override
            public void onBtnUserInfoClicked() {
                for (Listener listener : getListeners()) {
                    listener.onBtnUserInfoClicked();
                }
            }
        });

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Listener listener : getListeners()) {
                    listener.onBtnRetryClicked();
                }
            }
        });
    }


    @Override
    public void showQuickQuestion(List<QuickQuestionPM> bunchOfQuickQuestionPM) {
        throw new OnErrorNotImplementedException("MainMvcViewImp.showQuickQuestion is not implemented", null);
    }

    @Override
    public void showStatus(List<StatusPM> bunchOfStatusPm) {
        throw new OnErrorNotImplementedException("MainMvcViewImp.showStatus is not implemented", null);
    }

    @Override
    public void showSurveys(List<SurveyPM> surveys, List<SurveyTopicPM> topics, UserPM userPM) {
        layoutError.setVisibility(View.GONE);
        layoutMain.setVisibility(View.VISIBLE);
        layoutLoading.setVisibility(View.GONE);
        mAdapter.setUserAvatarUrl(userPM.getPictureUrl());
        if (rcvTopic.getAdapter() == null) {
            rcvTopic.setAdapter(mAdapter);
        }
        showSurveys(surveys, topics);
    }

    public void showSurveys(List<SurveyPM> surveys, List<SurveyTopicPM> topics) {
        this.surveys.clear();
        this.surveys.addAll(surveys);
        this.surveyTopics.clear();
        this.surveyTopics.addAll(topics);
        mAdapter.setDataSources(surveyTopics);
        mAdapter.setSurveys(surveys);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void showLoadingView(boolean showMain) {
        layoutError.setVisibility(View.GONE);
        layoutMain.setVisibility(View.GONE);
        layoutLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    public void showError(String errorMessage) {
        layoutError.setVisibility(View.VISIBLE);
        layoutMain.setVisibility(View.GONE);
        layoutLoading.setVisibility(View.GONE);
    }

    @Override
    public void hideQuickQuestion(String questionId) {

    }
}

package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.mvc_view;

import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.L;
import com.bumptech.glide.Glide;
import com.devlogs.osg.apolo.fptsurvey.R;
import com.devlogs.osg.apolo.fptsurvey.common.helper.ScreenDimensionSupport;
import com.devlogs.osg.apolo.fptsurvey.screen.common.basemvcview.BaseMvcView;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.mvc_view.quickquestion.QuickQuestionMvcView;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.mvc_view.quickquestion.QuickQuestionMvcViewImp;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.mvc_view.status.StatusMvcView;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.mvc_view.status.StatusMvcViewImp;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.QuickQuestionPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.StatusPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.SurveyPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.SurveyTopicPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.UserPM;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class MainMvcViewV2Imp extends BaseMvcView<MainMvcView.Listener> implements MainMvcView, TopicItemMvcView.Listener, QuickQuestionMvcView.Listener {
    private ScrollView scrvMain;
    private QuickQuestionMvcView mQuickQuestionMvcView;
    private StatusMvcView mStatusMvcView;
    private LinearLayout scrViewRootLayout;
    private TopicItemMvcView topicItemMvcView;
    private LinearLayout layoutLoading;
    private LinearLayout layoutError;
    private FrameLayout layoutMain;
    private ImageView imgUserInfo;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<TopicItemMvcView> mTopicItemMvcViews = new ArrayList();
    private LinearLayout layoutMarginBottom;

    private boolean isMargin = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public MainMvcViewV2Imp (LayoutInflater layoutInflater, ViewGroup container) {
        setRootView(layoutInflater.inflate(R.layout.layout_main_v2, container, false));
        scrvMain = findViewById(R.id.scrvMain);
        scrViewRootLayout = findViewById(R.id.scrRootLayout);
        mStatusMvcView = new StatusMvcViewImp(layoutInflater, null);
        mQuickQuestionMvcView = new QuickQuestionMvcViewImp(layoutInflater, null);
        layoutLoading = findViewById(R.id.layoutLoading);
        layoutMain = findViewById(R.id.layoutMain);
        layoutError = findViewById(R.id.layoutError);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        imgUserInfo = findViewById(R.id.imgAvatar);
        initMargin();
        addQuickQuestionSubView();
        mQuickQuestionMvcView.register(this);
        addStatusSubView();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                for (Listener listener : getListeners()) {
                    listener.onRefreshLayout();
                }
            }
        });
    }

    private void addQuickQuestionSubView () {
        Log.d("MainMvcV2", mStatusMvcView.getRootView().toString());
        scrViewRootLayout.addView(mQuickQuestionMvcView.getRootView());
    }

    private void addStatusSubView () {
        scrViewRootLayout.addView(mStatusMvcView.getRootView());
    }


    @Override
    public void showQuickQuestion(List<QuickQuestionPM> bunchOfQuickQuestionPM) {
        mQuickQuestionMvcView.showQuickQuestions(bunchOfQuickQuestionPM);
    }

    @Override
    public void showStatus(List<StatusPM> bunchOfStatusPm) {
        mStatusMvcView.showStatus(bunchOfStatusPm);
    }


    @Override
    public void showLoadingView(boolean showMain) {
        swipeRefreshLayout.setRefreshing(true);
        layoutError.setVisibility(View.GONE);
        if (showMain) {
            layoutMain.setVisibility(View.VISIBLE);
            layoutLoading.setVisibility(View.GONE);
        } else {
            layoutMain.setVisibility(View.GONE);
            layoutLoading.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void hideLoadingView() {
        swipeRefreshLayout.setRefreshing(false);
        layoutError.setVisibility(View.GONE);
        layoutMain.setVisibility(View.VISIBLE);
        layoutLoading.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMessage) {
        layoutError.setVisibility(View.VISIBLE);
        layoutMain.setVisibility(View.GONE);
        layoutLoading.setVisibility(View.GONE);
    }

    @Override
    public void hideQuickQuestion(String questionId) {
        mQuickQuestionMvcView.hideQuickQuestion(questionId);
    }

    @Override
    public void showSurveys(List<SurveyPM> surveys, List<SurveyTopicPM> topics, UserPM userPM) {

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(getContext());
        Glide
                .with(getContext())
                .load(userPM.getPictureUrl())
                .centerCrop()
                .placeholder(circularProgressDrawable)
                .into(imgUserInfo);

        imgUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Listener listener : getListeners()) {
                    listener.onBtnUserInfoClicked();
                }
            }
        });

        if (mTopicItemMvcViews.size() != topics.size()) {
            // clear the margin to make sure new item will not effected
            if (isMargin) {
                scrViewRootLayout.removeView(layoutMarginBottom);
                isMargin = false;
            }
            addTopicItemView(topics.size());
        }
            List<SurveyPM> mySurveys = new ArrayList();
            for (int i = 0; i < mTopicItemMvcViews.size(); i++) {
                mySurveys.clear();
                for (SurveyPM survey : surveys) {
                    if (survey.getTopicId().equals(topics.get(i).getId())) {
                        mySurveys.add(survey);
                    }
                }
                mTopicItemMvcViews.get(i).init(topics.get(i));
                mTopicItemMvcViews.get(i) .updateSurvey(mySurveys);
                mTopicItemMvcViews.get(i).register(this);
            }

            if (!isMargin) {
                scrViewRootLayout.addView(layoutMarginBottom);
                isMargin = true;
            }

    }

    private void addTopicItemView (int count) {
        for (TopicItemMvcView mTopicItemMvcView : mTopicItemMvcViews) {
            scrViewRootLayout.removeView(mTopicItemMvcView.getRootView());
        }
        mTopicItemMvcViews.clear();

        LinearLayout.LayoutParams topicLayoutParams;
        for (int i = 0; i < count; i++) {
            this.topicItemMvcView = new TopicItemMvcViewImp(LayoutInflater.from(getContext()), null);
            scrViewRootLayout.addView(topicItemMvcView.getRootView());
            if (i == 0) {
                topicLayoutParams = (LinearLayout.LayoutParams) this.topicItemMvcView.getRootView().getLayoutParams();
                topicLayoutParams.setMargins(topicLayoutParams.leftMargin, (int) ScreenDimensionSupport.instance.convertDpToPx(getContext(), 14), topicLayoutParams.topMargin, topicLayoutParams.bottomMargin);
            }
            mTopicItemMvcViews.add(topicItemMvcView);
        }
    }

    private void initMargin ( ) {
        layoutMarginBottom= new LinearLayout(getContext());
        layoutMarginBottom.setOrientation(LinearLayout.VERTICAL);
        layoutMarginBottom.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) ScreenDimensionSupport.instance.convertDpToPx(getContext(), 30)));
    }

    @Override
    public void onItemClickedListener(SurveyPM surveyPM) {
        for (Listener listener : getListeners()) {
            listener.onSurveyClicked(surveyPM);
        }
    }

    @Override
    public void onQuickQuestionBtnSubmitClicked(String questionId, int selectedAnswer) {
        for (Listener listener : getListeners()) {
            listener.onBtnQuickQuestionSubmitClicked(questionId, selectedAnswer);
        }
    }
}

package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.devlogs.osg.apolo.fptsurvey.R;
import com.devlogs.osg.apolo.fptsurvey.common.AppSetting;
import com.devlogs.osg.apolo.fptsurvey.common.AppSettingManager;
import com.devlogs.osg.apolo.fptsurvey.screen.common.MvcViewFactory;
import com.devlogs.osg.apolo.fptsurvey.screen.common.basepresentationstate.PresentationState;
import com.devlogs.osg.apolo.fptsurvey.screen.common.basepresentationstate.PresentationStateListener;
import com.devlogs.osg.apolo.fptsurvey.screen.common.dialog.base.DialogEventPublisher;
import com.devlogs.osg.apolo.fptsurvey.screen.common.dialog.base.DialogManager;
import com.devlogs.osg.apolo.fptsurvey.screen.common.dialog.showinguserinfo.UserInfoDialogPM;
import com.devlogs.osg.apolo.fptsurvey.screen.common.dialog.showinguserinfo.UserInfoFragmentDialog;
import com.devlogs.osg.apolo.fptsurvey.screen.loginscreen.controller.LoginActivity;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.mvc_view.MainMvcView;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.SurveyPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.UserPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.screenstate.MainScreenPresentationState;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.screenstate.MainScreenPresentationStateStore;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.controller.SurveyActivity;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements PresentationStateListener, MainMvcView.Listener, DialogEventPublisher.Listener {
    @Inject
    MainScreenPresentationStateStore mainStateStore;
    @Inject
    GetAllSurveyController getAllSurveyController;
    @Inject
    GetUserInfoController getUserInfoController;
    @Inject
    MvcViewFactory mvcViewFactory;
    @Inject
    DialogManager mDialogManager;
    @Inject
    SignOutController signOutController;
    @Inject
    AppSettingManager settingManager;
    @Inject
    GetStatusController getStatusController;
    @Inject
    GetQuickQuestionController getQuickQuestionController;
    @Inject
    PushQuickQuestionResultController pushQuickQuestionResultController;
    private MainMvcView mView;
    private boolean isShowMainWhenLoading = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = mvcViewFactory.getMainMvcView(null);
        setContentView(mView.getRootView());
        mainStateStore.init(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainStateStore.register(this);
        restoreState();
        mView.register(this);
    }

    private int getItemColor (int pos) {
        int colorIndex = pos % 5;

        switch (colorIndex) {
            case 0 : {return getResources().getColor(R.color.colorRed);}
            case 1 : {return getResources().getColor(R.color.colorHeavyBlue);}
            case 2 : {return getResources().getColor(R.color.colorLightCyan);}
            case 3 : {return getResources().getColor(R.color.colorPurple);}
            default : {return getResources().getColor(R.color.colorFreshOrange);}
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mainStateStore.onSavedInstanceState(outState);
    }

    private void restoreState () {
        MainScreenPresentationState oldState = (MainScreenPresentationState) mainStateStore.restoreState();
        if (oldState == null) {
            oldState = new MainScreenPresentationState.Loading();
        }
        mainStateStore.updateState(oldState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainStateStore.unRegister(this);
        mView.unRegister(this);
    }

    @Override
    public void onStateChanged(PresentationState state) {
        if (MainScreenPresentationState.Loading.class.equals(state.getClass())) {
            mView.showLoadingView(isShowMainWhenLoading);
            getAllSurveyController.getAllSurvey();
            getUserInfoController.getUser();
            Log.d("MainActivity", "Loading state");
        }
        if (MainScreenPresentationState.Error.class.equals(state.getClass())) {
            mView.showError(((MainScreenPresentationState.Error) state).errorMessage);
            Log.d("MainActivity", "error state: " + ((MainScreenPresentationState.Error) state).errorMessage);
        }
        if (MainScreenPresentationState.FinishedState.class.equals(state.getClass())) {
            getStatusController.getAllStatus();
            getQuickQuestionController.getAllQuickQuestion(((MainScreenPresentationState.FinishedState) state).userPM.getEmail());
            mView.showSurveys(
                    ((MainScreenPresentationState.FinishedState) state).surveyPMS,
                    ((MainScreenPresentationState.FinishedState) state).surveyTopicPMS,
                    ((MainScreenPresentationState.FinishedState) state).userPM);
            List<SurveyPM> survey = ((MainScreenPresentationState.FinishedState) state).surveyPMS;
            Log.d("MainActivity", "Finished state" + ((MainScreenPresentationState.FinishedState) state).surveyPMS.get(0).getTopicId());
            for (int i = 0; i < survey.size(); i++) {
                survey.get(i).setBackgroundColor(getItemColor(i));
            }
        }
    }

    @Override
    public void onEffectOccur(PresentationState.PresentationEffect effect) {
        if (effect == null) {return;}
        if (MainScreenPresentationState.MainScreenPresentationEffect.StatusLoaded.class.equals(effect.getClass())) {
            mView.showStatus(((MainScreenPresentationState.MainScreenPresentationEffect.StatusLoaded) effect).statusPMS);
        }

        if (MainScreenPresentationState.MainScreenPresentationEffect.QuickQuestionLoaded.class.equals(effect.getClass())) {
            mView.showQuickQuestion(((MainScreenPresentationState.MainScreenPresentationEffect.QuickQuestionLoaded) effect).quickQuestionPMS);
            mView.hideLoadingView();
        }
    }

    @Override
    public void onSurveyClicked(SurveyPM surveyPM) {
        SurveyActivity.start(this, surveyPM.getTitle(), surveyPM.getId());
    }

    @Override
    public void onBtnRetryClicked() {
        mainStateStore.updateState(new MainScreenPresentationState.Loading());
    }

    @Override
    public void onBtnUserInfoClicked() {
        MainScreenPresentationState currentState = mainStateStore.getCurrentState();

        if (currentState == null && !(currentState instanceof MainScreenPresentationState.FinishedState)) {
            return;
        }

        MainScreenPresentationState.FinishedState finishedStateState = (MainScreenPresentationState.FinishedState) currentState;
        UserPM user = finishedStateState.userPM;

        mDialogManager.showUserInfoDialog(new UserInfoDialogPM(user.getName(), user.getStudentId(), user.getPictureUrl())).register(this);
    }

    @Override
    public void onRefreshLayout() {
        isShowMainWhenLoading = true;
        mainStateStore.updateState(new MainScreenPresentationState.Loading());
    }

    @Override
    public void onBtnQuickQuestionSubmitClicked(String questionId, int selectedId) {
        if (mainStateStore.getCurrentState() instanceof  MainScreenPresentationState.FinishedState) {
            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
            mView.hideQuickQuestion(questionId);
            pushQuickQuestionResultController.push(questionId, selectedId, ((MainScreenPresentationState.FinishedState) mainStateStore.getCurrentState()).userPM.getEmail())
            .subscribe(new CompletableObserver() {
                @Override
                public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                }

                @Override
                public void onComplete() {

                }

                @Override
                public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                    Log.d("MainActivivty", "QuickQuestionFailed: " + questionId + " " + e.getMessage());
                    Toast.makeText(MainActivity.this, "Failed refresh to try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onDialog(Object any) {
         if (UserInfoFragmentDialog.Event.class.equals(any.getClass())) {
             UserInfoFragmentDialog.Event event = (UserInfoFragmentDialog.Event) any;
             switch (event) {
                 case BtnSignOutCLicked: {
                     updateAutoLoginSetting();
                     signOutController.signOut();
                     mDialogManager.dismissUserInfoFragmentDialog();
                     finish();
                     LoginActivity.start(this, Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                     break;
                 }
                 default: break;
             }
         }
    }

    private void updateAutoLoginSetting () {
        settingManager.updateSetting(new AppSettingManager.SettingCustomizer() {
            @Override
            public AppSetting custom(AppSetting currentSetting) {
                currentSetting.autoLogin = false;
                return currentSetting;
            }
        });
    }

    public static void start (Context context, int... flags) {
        Intent intent = new Intent(context, MainActivity.class);

        for (int flag : flags) {
            intent.setFlags(flag);
        }
        context.startActivity(intent);
    }
}

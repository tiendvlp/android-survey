package com.devlogs.osg.apolo.fptsurvey.screen.loginscreen.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import com.devlogs.osg.apolo.fptsurvey.R;
import com.devlogs.osg.apolo.fptsurvey.common.AppSetting;
import com.devlogs.osg.apolo.fptsurvey.common.AppSettingManager;
import com.devlogs.osg.apolo.fptsurvey.screen.common.MvcViewFactory;
import com.devlogs.osg.apolo.fptsurvey.screen.common.basepresentationstate.PresentationState;
import com.devlogs.osg.apolo.fptsurvey.screen.common.basepresentationstate.PresentationStateListener;
import com.devlogs.osg.apolo.fptsurvey.screen.loginscreen.mvc_view.LoginMvcView;
import com.devlogs.osg.apolo.fptsurvey.screen.loginscreen.screenstate.LoginScreenState;
import com.devlogs.osg.apolo.fptsurvey.screen.loginscreen.screenstate.LoginStateStore;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.controller.MainActivity;
import com.devlogs.osg.apolo.fptsurvey.survey.GetAllSurveyUseCaseSync;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Retrofit;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity implements LoginMvcView.Listener, PresentationStateListener {
    private GoogleSignInClient mGoogleSignIn;
    @Inject
    Retrofit retrofit;
    @Inject
    public GetAllSurveyUseCaseSync mGetAllSurveyUsecase;
    @Inject
    MvcViewFactory mvcViewFactory;
    @Inject
    LoginStateStore stateStore;
    @Inject
    LoginController loginController;
    @Inject
    AppSettingManager settingManager;
    private LoginMvcView mView;
    private boolean isAutoLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mView = mvcViewFactory.getLoginMvcView(null);
        setContentView(mView.getRootView());
        getAppSetting ();
        configureGGSignIn ();
        stateStore.init(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        stateStore.onSavedInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mView.register(this);
        stateStore.register(this);
        Toast.makeText(this, "restoring", Toast.LENGTH_SHORT).show();
        stateStore.restoreState();
    }

    private void getAppSetting () {
        isAutoLoggedIn = settingManager.getAppSetting().autoLogin;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mView.resumeAnim();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mView.pauseAnim();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stateStore.unRegister(this);
        mView.unRegister(this);
    }

    private void configureGGSignIn () {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mGoogleSignIn = GoogleSignIn.getClient(this, gso);
        if (isAutoLoggedIn) {
            loginSilent();
        }
    }

    private void loginSilent () {
        mGoogleSignIn.silentSignIn().addOnCompleteListener(new OnCompleteListener<GoogleSignInAccount>() {
            @Override
            public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                handleSignInResult(task);
            }
        });
    }

    private void signin () {
        Intent signInIntent = mGoogleSignIn.getSignInIntent();
        startActivityForResult(signInIntent, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.d("GG token","token: "+ account.getIdToken());
            loginController.loginWithFptGmail(account.getIdToken());
            updateAutoLoginSetting();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
        }
    }

    private void updateAutoLoginSetting () {
        settingManager.updateSetting(new AppSettingManager.SettingCustomizer() {
            @Override
            public AppSetting custom(AppSetting currentSetting) {
                currentSetting.autoLogin = true;
                isAutoLoggedIn = true;
                return currentSetting;
            }
        });
    }

    @Override
    public void onBtnLoginWithGGClicked() {
        signin();
    }

    @Override
    public void onStateChanged(PresentationState state) {
        if (LoginScreenState.LoadingState.class.equals(state.getClass())) {
            Log.d("LoginActivity", "Loading");
            mView.showLoadingView();
        }
        if (LoginScreenState.LoggedInState.class.equals(state.getClass())) {
            Log.d("LoginActivity", "LoggedIn");
            Toast.makeText(this, "loggedIn", Toast.LENGTH_SHORT).show();
            MainActivity.start(this, Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
        }

        if (LoginScreenState.NotLoggedInState.class.equals(state.getClass())) {
            Log.d("LoginActivity", "NotLoggedIn");
            mView.hideLoadingView();
        }
    }

    @Override
    public void onEffectOccur(PresentationState.PresentationEffect effect) {

    }

    public static void start (Context context, int... flags) {
        Intent intent = new Intent(context, LoginActivity.class);
        for (int flag : flags) {
            intent.setFlags(flag);
        }
        context.startActivity(intent);
    }
}
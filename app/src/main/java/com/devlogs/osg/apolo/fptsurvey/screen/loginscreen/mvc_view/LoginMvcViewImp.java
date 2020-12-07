package com.devlogs.osg.apolo.fptsurvey.screen.loginscreen.mvc_view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.airbnb.lottie.LottieAnimationView;
import com.devlogs.osg.apolo.fptsurvey.R;
import com.devlogs.osg.apolo.fptsurvey.screen.common.basemvcview.BaseMvcView;

public class LoginMvcViewImp extends BaseMvcView<LoginMvcView.Listener> implements LoginMvcView {
    private Button btnLoginWithGG;
    private LottieAnimationView lottieMainAnim;
    private ProgressBar lnLoading;

    public LoginMvcViewImp(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.layout_login, container, false));
        btnLoginWithGG = findViewById(R.id.btnGGLogin);
        Log.d("LoginMvcViewImp", btnLoginWithGG.toString());
        lnLoading = findViewById(R.id.lnLoading);
        lottieMainAnim = findViewById(R.id.lottieMainAnim);
        addEvents();
    }

    private void addEvents () {
        btnLoginWithGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (LoginMvcView.Listener listener : getListeners()) {
                    listener.onBtnLoginWithGGClicked();
                }
            }
        });
    }

    @Override
    public void hideLoadingView() {
        lnLoading.setVisibility(View.GONE);
        btnLoginWithGG.setEnabled(true);
        btnLoginWithGG.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadingView() {
        lnLoading.setVisibility(View.VISIBLE);
        btnLoginWithGG.setEnabled(false);
        btnLoginWithGG.setVisibility(View.INVISIBLE);
    }

    @Override
    public void pauseAnim() {
        lottieMainAnim.pauseAnimation();
    }

    @Override
    public void resumeAnim() {
        lottieMainAnim.resumeAnimation();
    }
}

package com.devlogs.osg.apolo.fptsurvey.screen.endscreen.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.devlogs.osg.apolo.fptsurvey.R;
import com.devlogs.osg.apolo.fptsurvey.screen.common.MvcViewFactory;
import com.devlogs.osg.apolo.fptsurvey.screen.endscreen.mvcview.EndSurveyMvcView;
import com.devlogs.osg.apolo.fptsurvey.surveyresult.GetSurveyResultLayoutUseCaseSync;
import com.flipkart.android.proteus.Proteus;
import com.flipkart.android.proteus.ProteusBuilder;
import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusLayoutInflater;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.gson.ProteusTypeAdapterFactory;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;
import com.google.gson.stream.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import retrofit2.Retrofit;

@AndroidEntryPoint
public class EndActivity extends AppCompatActivity implements EndSurveyMvcView.Listener {
    @Inject
    MvcViewFactory factory;

    @Inject
    Retrofit mRetrofit;
    @Inject
    GetSurveyResultLayoutUseCaseSync getSurveyResultLayoutUseCaseSync;
    private EndSurveyMvcView mMvcView;
    private String surveyResultId = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMvcView = factory.getEndSurveyMvcView(null);
        setContentView(mMvcView.getRootView());

        if (getIntent() != null) {
            String surveyResultId = getIntent().getStringExtra(SURVEY_RESULT_ID_INTENT_DATA);
            String surveyTitle = getIntent().getStringExtra(SURVEY_TITLE_INTENT_DATA);
            mMvcView.setTitle(surveyTitle);
            
            Log.d("tiendang-debug", "Found survey result id" + surveyResultId);
                if (surveyResultId != null && !surveyResultId.isEmpty()) {
                    getSurveyResultLayoutUseCaseSync.execute(surveyResultId).subscribe(new SingleObserver<String>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NonNull String s) {
                            try {
                                JSONObject root = new JSONObject(s);
                                JSONObject data = root.getJSONObject("surveyresult");
                                JSONObject layout = root.getJSONObject("clientLayout");
                                Log.d("oklala", layout.toString());
//                                mMvcView.drawUI(layout.toString(), data.toString() );
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.d("EndActivity", e.getMessage());
                        }
                    });
                }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMvcView.register(this);
    }

    @Override
    protected void onStop() {
        mMvcView.unRegister(this);
        super.onStop();
    }

    public static final String SURVEY_RESULT_ID_INTENT_DATA = "SURVEYRESULT";
    public static final String SURVEY_TITLE_INTENT_DATA = "SURVEYTITLE";

    public static void start (String surveyResultId, String surveyTitle, Context context, int...flags) {
        Intent intent = new Intent(context, EndActivity.class);
        intent.putExtra(SURVEY_RESULT_ID_INTENT_DATA, surveyResultId);
        intent.putExtra(SURVEY_TITLE_INTENT_DATA, surveyTitle);
        for (int flag : flags) {
            intent.setFlags(flag);
        }

        context.startActivity(intent);
    }
}

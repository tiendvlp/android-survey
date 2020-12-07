package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.mvc_view.quickquestion;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.devlogs.osg.apolo.fptsurvey.R;
import com.devlogs.osg.apolo.fptsurvey.screen.common.basemvcview.BaseMvcView;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.controller.QuickQuestionAdapter;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.QuickQuestionPM;

import java.util.List;

public class QuickQuestionMvcViewImp extends BaseMvcView<QuickQuestionMvcView.Listener> implements QuickQuestionMvcView, QuickQuestionAdapter.Listener {

    private RecyclerView rcvQuickQuestion;
    private QuickQuestionAdapter mQuickQuestionAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public QuickQuestionMvcViewImp (LayoutInflater layoutInflater, ViewGroup container) {
        setRootView(layoutInflater.inflate(R.layout.layout_quickquestion, container, false));
        rcvQuickQuestion = findViewById(R.id.rcvQuickQuestions);
        mQuickQuestionAdapter = new QuickQuestionAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        SnapHelper linearSnapHelper = new PagerSnapHelper();
        linearSnapHelper.attachToRecyclerView(rcvQuickQuestion);
        rcvQuickQuestion.setLayoutManager(layoutManager);
        rcvQuickQuestion.setAdapter(mQuickQuestionAdapter);

        mQuickQuestionAdapter.register(this);

    }

    @Override
    public void showQuickQuestions(List<QuickQuestionPM> bunchOfQuickQuestionPm) {
        mQuickQuestionAdapter.setQuestionPMS(bunchOfQuickQuestionPm);
    }

    @Override
    public void hideQuickQuestion(String questionId) {

        for (int i = 0; i < mQuickQuestionAdapter.getQuestionPMS().size(); i++) {
            if (questionId.equals(mQuickQuestionAdapter.getQuestionPMS().get(i).getQuestionId())) {
                mQuickQuestionAdapter.getQuestionPMS().remove(i);
                mQuickQuestionAdapter.notifyItemRemoved(i);
            }
        }
    }

    @Override
    public void onBtnSubmitClicked(String question, int answer) {
        for (Listener listener : getListeners()) {
            listener.onQuickQuestionBtnSubmitClicked(question,answer);
        }
    }
}

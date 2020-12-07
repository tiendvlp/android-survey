package com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.mvcview;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.devlogs.osg.apolo.fptsurvey.R;
import com.devlogs.osg.apolo.fptsurvey.screen.common.basemvcview.BaseMvcView;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.presentationmodel.QuestionPM;

public class ItemSurveyMvcViewImp extends BaseMvcView<ItemSurveyMvcView.Listener> implements ItemSurveyMvcView {
    private TextView txtQuestionContent;
    private RadioGroup rdgr;
    private QuestionPM questionPM;

    public ItemSurveyMvcViewImp (LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.item_question, parent, false));
        txtQuestionContent = findViewById(R.id.txtQuestionContent);
        rdgr = findViewById(R.id.rbtngrAnswer);

        rdgr.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == -1) {
                    return;
                }
                for (Listener listener : getListeners()) {
                    listener.onAnswerChecked(questionPM, i);
                }
            }
        });
    }

    @Override
    public void showQuestion(QuestionPM question) {
        this.questionPM = question;
        txtQuestionContent.setText(question.getQuestionContent());
        RadioButton rbtn;
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 15);
        this.rdgr.removeAllViews();

        for (int i = 0; i < question.getAnswers().length; i++) {
            rbtn = (RadioButton) LayoutInflater.from(getContext()).inflate(R.layout.item_answer, null, false);
            rbtn.setLayoutParams(params);
            rbtn.setId(i);
            if (i == question.getCheckedAnswer()) {
                rbtn.setChecked(true);
            }
            rbtn.setText(question.getAnswers()[i]);
            rdgr.addView(rbtn);
        }
    }

}

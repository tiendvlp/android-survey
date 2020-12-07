package com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EdgeEffect;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devlogs.osg.apolo.fptsurvey.R;
import com.devlogs.osg.apolo.fptsurvey.screen.common.ObservableRecyclerViewAdapter;
import com.devlogs.osg.apolo.fptsurvey.screen.common.animation.BaseVHAnimation;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.mvcview.ItemSurveyMvcView;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.mvcview.ItemSurveyMvcViewImp;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.presentationmodel.QuestionPM;
import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.presentationmodel.SurveyPM;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class QuestionRcvAdapter extends ObservableRecyclerViewAdapter<QuestionRcvAdapter.Listener, RecyclerView.ViewHolder> implements ItemSurveyMvcView.Listener {


    public interface Listener {
        public void onItemClicked (UserSelection userSelection);
    }


    public class UserSelection {
        public int question;
        public int answer;

        public UserSelection(int question, int answer) {
            this.question = question;
            this.answer = answer;
        }
    }

    private class HeaderVH extends BaseVHAnimation {

        private TextView txtHeader;

        public HeaderVH(@NonNull View itemView) {
            super(itemView);
            txtHeader = itemView.findViewById(R.id.txtHeader);
        }

        void bind(String header) {
            this.txtHeader.setText(header);
        }
    }

    private class ItemVH extends BaseVHAnimation {
        ItemSurveyMvcView itemMvcView;

        public ItemVH(@NonNull ItemSurveyMvcView itemView) {
            super(itemView.getRootView());
            this.itemMvcView = itemView;
        }

        void bind(QuestionPM question) {
            this.itemMvcView.showQuestion(question);

            if (getAdapterPosition() == getItemCount() - 1) {
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
                params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, 240);
                itemView.setLayoutParams(params);
            }

        }

        private float convertDpToPx(Context context, float dp) {
            return dp * context.getResources().getDisplayMetrics().density;
        }
    }

    public static int HEADER_VIEW = 0;
    public static int ITEM_VIEW = 1;
    private SurveyPM surveyPm;
    private List<QuestionPM> questionPM;
    private Listener mListener;

    public QuestionRcvAdapter(SurveyPM survey, List<QuestionPM> questionPM) {
        surveyPm = survey;
        this.questionPM = questionPM;
    }

    @Override
    public void register(Listener listener) {
        mListener = listener;
    }

    @Override
    public void unRegister(Listener listener) {
        mListener = null;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == HEADER_VIEW) {
            return new HeaderVH(layoutInflater.inflate(R.layout.item_header, parent, false));
        }
        ItemVH itemVH = new ItemVH(new ItemSurveyMvcViewImp(layoutInflater, parent));

        return itemVH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderVH) {
            ((HeaderVH) holder).bind(surveyPm.getSurveyName());
        }

        if (holder instanceof ItemVH) {
            ((ItemVH) holder).itemMvcView.register(this);
            ((ItemVH) holder).bind(this.questionPM.get(position - 1));
        }
    }

    @Override
    public void onAnswerChecked(QuestionPM question, int answer) {
        mListener.onItemClicked(new UserSelection(questionPM.indexOf(question), answer));
    }

    @Override
    public int getItemCount() {
        return questionPM.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_VIEW;
        }
        return ITEM_VIEW;
    }

}

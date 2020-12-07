package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.controller;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devlogs.osg.apolo.fptsurvey.R;
import com.devlogs.osg.apolo.fptsurvey.screen.common.ObservableRecyclerViewAdapter;
import com.devlogs.osg.apolo.fptsurvey.screen.common.animation.BaseVHAnimation;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.SurveyPM;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class SurveyRcvAdapter extends ObservableRecyclerViewAdapter<SurveyRcvAdapter.Listener,SurveyRcvAdapter.ViewHolder> {

    public interface Listener {
        void onItemClicked (SurveyPM surveyPM);
    }

    public class ViewHolder extends BaseVHAnimation {
        private TextView txtTitle;
        private TextView txtDescription;
        private TextView txtNumOfQuestion;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtNumOfQuestion = itemView.findViewById(R.id.txtNumberOfQuestion);
        }

        void bind (SurveyPM survey) {
            if (getAdapterPosition() == 0) {
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
                params.setMargins((int) convertDpToPx(itemView.getContext(), 20) ,0,params.rightMargin,0);
                itemView.setLayoutParams(params);
            }
            if (getAdapterPosition() == getItemCount()-1) {
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
                params.setMargins(params.leftMargin ,0,(int) convertDpToPx(itemView.getContext(), 20),0);
                itemView.setLayoutParams(params);
            }

            txtNumOfQuestion.setText( survey.getNumOfQuestion() + " Questions");
            itemView.setBackgroundTintList(ColorStateList.valueOf(survey.getBackgroundColor()));
            txtTitle.setText(survey.getTitle());
            txtDescription.setText(survey.getDescription());
        }
        private float convertDpToPx(Context context, float dp) {
            return dp * context.getResources().getDisplayMetrics().density;
        }

    }

    private Listener mListener;
    private List<SurveyPM> dataSources = new ArrayList();
    public SurveyRcvAdapter() {
    }

    public List<SurveyPM> getDataSources() {
        return dataSources;
    }

    public void setDataSources(List<SurveyPM> dataSources) {
        this.dataSources = dataSources;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_survey, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(dataSources.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClicked(dataSources.get(position));
            }
        });
    }


    @Override
    public void register(Listener listener) {
        mListener = listener;
    }

    @Override
    public void unRegister(Listener listener) {
        mListener = null;
    }

    @Override
    public int getItemCount() {
        return dataSources.size();
    }
}

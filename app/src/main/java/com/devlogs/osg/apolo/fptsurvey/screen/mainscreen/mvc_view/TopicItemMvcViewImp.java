package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.mvc_view;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.devlogs.osg.apolo.fptsurvey.R;
import com.devlogs.osg.apolo.fptsurvey.screen.common.basemvcview.BaseMvcView;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.controller.SurveyRcvAdapter;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.SurveyPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.SurveyTopicPM;
import java.util.ArrayList;
import java.util.List;

public class TopicItemMvcViewImp extends BaseMvcView<TopicItemMvcView.Listener> implements TopicItemMvcView {
    private RecyclerView rcvSurvey;
    private TextView txtProgress;
    private TextView txtHeader;
    private SurveyRcvAdapter mAdapter;
    private ArrayList<SurveyPM> dataSources = new ArrayList();

    public TopicItemMvcViewImp(LayoutInflater layoutInflater, ViewGroup container) {
        setRootView(layoutInflater.inflate(R.layout.item_topic, container, false));
        rcvSurvey = findViewById(R.id.rcvSurvey);
        txtProgress = findViewById(R.id.txtProgress);
        txtHeader = findViewById(R.id.txtHeader);

        mAdapter = new SurveyRcvAdapter();
        mAdapter.setDataSources(dataSources);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        layoutManager.setSmoothScrollbarEnabled(true);
        rcvSurvey.setLayoutManager(layoutManager);
        rcvSurvey.setAdapter(mAdapter);

        mAdapter.register(surveyPM -> {
            for (Listener listener : getListeners()) {
                listener.onItemClickedListener(surveyPM);
            }
        });
    }

    @Override
    public void updateSurvey(List<SurveyPM> surveys) {
        dataSources.clear();
        dataSources.addAll(surveys);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void init(SurveyTopicPM surveyTopic) {
        txtProgress.setText(surveyTopic.getProgress());
        txtHeader.setText(surveyTopic.getTitle());
    }
}

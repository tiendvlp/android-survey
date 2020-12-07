package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.mvc_view.status;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.devlogs.osg.apolo.fptsurvey.R;
import com.devlogs.osg.apolo.fptsurvey.screen.common.basemvcview.BaseMvcView;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.controller.StatusAdapter;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.StatusPM;

import java.util.List;

public class StatusMvcViewImp extends BaseMvcView<StatusMvcView.Listener> implements StatusMvcView {
    private RecyclerView rcvStatus;
    private StatusAdapter mStatusAdapter;

    public StatusMvcViewImp (LayoutInflater layoutInflater, ViewGroup container) {
        setRootView(layoutInflater.inflate(R.layout.layout_status, null, false));
        rcvStatus = findViewById(R.id.rcvStatus);
        mStatusAdapter = new StatusAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        new PagerSnapHelper().attachToRecyclerView(rcvStatus);
        rcvStatus.setLayoutManager(layoutManager);
        rcvStatus.setAdapter(mStatusAdapter);
    }

    @Override
    public void showStatus(List<StatusPM> bunchOfStatusPM) {
        mStatusAdapter.setStatusPMS(bunchOfStatusPM);
    }
}

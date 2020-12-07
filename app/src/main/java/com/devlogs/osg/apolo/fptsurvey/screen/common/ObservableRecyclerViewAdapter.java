package com.devlogs.osg.apolo.fptsurvey.screen.common;

import androidx.recyclerview.widget.RecyclerView;

public abstract class ObservableRecyclerViewAdapter<LISTENER, VIEWHOLDER  extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VIEWHOLDER> {
    public abstract void register (LISTENER listener);
    public abstract void unRegister (LISTENER listener);
}

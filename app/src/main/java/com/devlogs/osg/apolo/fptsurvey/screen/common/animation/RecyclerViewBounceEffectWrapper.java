package com.devlogs.osg.apolo.fptsurvey.screen.common.animation;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerViewBounceEffectWrapper {
    public void activateEffect (RecyclerView recycler) {
        recycler.setEdgeEffectFactory(new RecyclerViewBounceEffect().getBounceEffect());
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                for (int index = 0; index <= recyclerView.getChildCount(); ++index) {
                    if (recycler.getChildAt(index) == null) {continue;}
                    Log.d("SurveyMvcView", "gog go go" + index);
                    BaseVHAnimation holder = (BaseVHAnimation) recycler.getChildViewHolder(recycler.getChildAt(index));
                    holder.rotation
                            .setStartVelocity(holder.currentVelocity - dx * RecyclerViewBounceEffect.SCROLL_ROTATION_MAGNITUDE)
                            .start();
                }
            }
        });
    }
}

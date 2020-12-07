package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.controller;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.devlogs.osg.apolo.fptsurvey.R;
import com.devlogs.osg.apolo.fptsurvey.common.helper.ScreenDimensionSupport;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.StatusPM;

import java.util.ArrayList;
import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter <StatusAdapter.MyViewHolder> {


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private class SubItemView {
            private TextView txtStatusType;
            private TextView txtStatusBody;
            private ImageView imgStatus;
            private View mView;

            private SubItemView (View subItemView) {
                mView = subItemView;
                txtStatusType = subItemView.findViewById(R.id.txtStatusType);
                imgStatus = subItemView.findViewById(R.id.imgStatusType);
                txtStatusBody = subItemView.findViewById(R.id.txtStatusBody);
            }

            private void bind (StatusPM status) {
                txtStatusBody.setText(status.getStatusBody());
                txtStatusType.setText(status.getType());
                String iconName = "icon_finished";
                Drawable myIcon;
                if (status.getType().equals("finish") ) {
                     myIcon =  ResourcesCompat.getDrawable(mView.getResources(),
                            R.drawable.icon_finished, null);
                    mView.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(mView.getResources(), R.color.color_background_status_finish, null)));

                } else {
                     myIcon =  ResourcesCompat.getDrawable(mView.getResources(),
                            R.drawable.icon_info, null);
                    mView.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(mView.getResources(), R.color.color_background_status_info, null)));
                }
                imgStatus.setImageDrawable(myIcon);
            }

        }

        private SubItemView sub1;
        private SubItemView sub2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sub1 = new SubItemView(itemView.findViewById(R.id.firstSubStatus));
            sub2 = new SubItemView(itemView.findViewById(R.id.secondSubStatus));
        }

        private void bind (StatusPM status1, StatusPM status2) {
            sub1.bind(status1);
            if (status2 != null) {
                sub2.bind(status2);
            }
        }

        private void updateItemSize (int position) {
            int itemWidth = (int) ((ScreenDimensionSupport.instance.getScreenSize(itemView.getContext()).getWidth())-ScreenDimensionSupport.instance.convertDpToPx(itemView.getContext(), 40));
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            params.width = itemWidth;
            if (position==0) {
                params.setMargins((int) convertDpToPx(itemView.getContext(), 20f), params.topMargin, params.rightMargin, params.bottomMargin);
            }
            if  (position == getItemCount()-1) {
                params.setMargins(params.leftMargin, params.topMargin,(int) convertDpToPx(itemView.getContext(), 20f), params.bottomMargin);
                itemView.setLayoutParams(params);
            }
            itemView.setLayoutParams(params);
        }

        private float convertDpToPx(Context context, float dp) {
            return dp * context.getResources().getDisplayMetrics().density;
        }
    }

    private List<StatusPM> statusPMS = new ArrayList();


    public StatusAdapter() {
    }

    public List<StatusPM> getStatusPMS() {
        return statusPMS;
    }

    public void setStatusPMS(List<StatusPM> statusPMS) {
        this.statusPMS.clear();
        this.statusPMS.addAll(statusPMS);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_status, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int realPosition = position * 2;

        StatusPM status2 = null;
        if (realPosition + 1 < statusPMS.size()) {
            status2 = statusPMS.get(position+1);
        }
        StatusPM status1 = statusPMS.get(realPosition);

        holder.bind(status1, status2);
        holder.updateItemSize(position);
    }

    @Override
    public int getItemCount() {
        return (int) Math.ceil(statusPMS.size()/2);
    }

}

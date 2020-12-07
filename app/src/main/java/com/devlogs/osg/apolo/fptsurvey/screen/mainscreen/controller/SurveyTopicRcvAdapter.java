package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.controller;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.devlogs.osg.apolo.fptsurvey.R;
import com.devlogs.osg.apolo.fptsurvey.screen.common.ObservableRecyclerViewAdapter;
import com.devlogs.osg.apolo.fptsurvey.screen.common.animation.BaseVHAnimation;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.mvc_view.TopicItemMvcView;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.mvc_view.TopicItemMvcViewImp;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.SurveyPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.SurveyTopicPM;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class SurveyTopicRcvAdapter extends ObservableRecyclerViewAdapter<SurveyTopicRcvAdapter.Listener, RecyclerView.ViewHolder> implements TopicItemMvcView.Listener {
    public static final int HEADER_TYPE = 0;
    public static final int ITEM_TYPE = 1;

    @Override
    public void register(Listener listener) {
        mListener = listener;
    }

    @Override
    public void unRegister(Listener listener) {
        mListener = null;
    }

    public interface Listener {
        void onItemClicked (SurveyPM surveyPM);
        void onBtnUserInfoClicked ();
    }

    class HeaderViewHolder extends BaseVHAnimation {
        private TextView txtTitle;
        private CircleImageView imgAvatar;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtHeader);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
        }

        public void bind (String avatarUrl, View.OnClickListener onBtnUserInfoClickedListener) {
            txtTitle.setText("Survey");
            if (avatarUrl.isEmpty()) {return;}
            CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(itemView.getContext());
            Glide
                    .with(itemView.getContext())
                    .load(avatarUrl)
                    .centerCrop()
                    .placeholder(circularProgressDrawable)
                    .into(imgAvatar);

            imgAvatar.setOnClickListener(onBtnUserInfoClickedListener);
        }
    }

    class ItemViewHolder extends BaseVHAnimation {
        private TopicItemMvcView topicItemMvcView;

        public ItemViewHolder(@NonNull TopicItemMvcView topicItemMvcView) {
            super(topicItemMvcView.getRootView());
            this.topicItemMvcView = topicItemMvcView;
        }

         public void bind (SurveyTopicPM surveyTopic, List<SurveyPM> survey) {
            topicItemMvcView.init(surveyTopic);
            topicItemMvcView.updateSurvey(survey);
         }
    }

    private List<SurveyTopicPM> dataSources = new ArrayList();
    private List<SurveyPM> surveys = new ArrayList();
    private Listener mListener;
    private String userAvatarUrl = "";

    public SurveyTopicRcvAdapter () {
    }

    public List<SurveyTopicPM> getDataSources() {
        return dataSources;
    }

    public void setDataSources(List<SurveyTopicPM> dataSources) {
        this.dataSources = dataSources;
    }

    public List<SurveyPM> getSurveys() {
        return surveys;
    }

    public void setSurveys(List<SurveyPM> surveys) {
        this.surveys = surveys;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = parent.getContext().getSystemService(LayoutInflater.class);
        if (viewType == HEADER_TYPE) {
            return new HeaderViewHolder(inflater.inflate(R.layout.item_main_header, parent, false));
        }
        ItemViewHolder itemViewHolder = new ItemViewHolder(new TopicItemMvcViewImp(inflater, parent));
        return itemViewHolder;
    }

    @Override
    public void onItemClickedListener(SurveyPM surveyPM) {
        mListener.onItemClicked(surveyPM);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).bind(userAvatarUrl, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onBtnUserInfoClicked();
                }
            });
            return;
        }

        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        ArrayList<SurveyPM> surveyBelongToOneTopic = new ArrayList ();
        for (SurveyPM survey : this.surveys) {
            if (survey.getTopicId().equals(dataSources.get(position-1).getId()) ) {
                surveyBelongToOneTopic.add(survey);
            }
        }
        itemViewHolder.topicItemMvcView.register(this);
        itemViewHolder.bind(dataSources.get(position - 1), surveyBelongToOneTopic);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_TYPE;
        }
        return ITEM_TYPE;
    }

    @Override
    public int getItemCount() {
        return dataSources.size() + 1;
    }
}

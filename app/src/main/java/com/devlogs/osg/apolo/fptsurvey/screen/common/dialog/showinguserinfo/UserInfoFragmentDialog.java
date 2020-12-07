package com.devlogs.osg.apolo.fptsurvey.screen.common.dialog.showinguserinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.devlogs.osg.apolo.fptsurvey.R;
import com.devlogs.osg.apolo.fptsurvey.screen.common.dialog.base.BaseFragmentDialog;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoFragmentDialog extends BaseFragmentDialog {

    public enum Event {
        BtnSignOutCLicked
    }

    public static String TAG = "UserInfoFragmentDialog";

    private UserInfoDialogPM mUserInfo;
    private TextView txtUserName;
    private TextView txtStudentId;
    private CircleImageView imgAvatar;
    private Button btnCancel;
    private Button btnSignOut;

    public static UserInfoFragmentDialog newInstance (UserInfoDialogPM userInfo) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("UserInfo", userInfo);

        UserInfoFragmentDialog dialog = new UserInfoFragmentDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserInfo = (UserInfoDialogPM) requireArguments().getSerializable("UserInfo");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.layout_dialog_showuserinfo, container, false);
        addControls(view);
        bind();
        return view;
    }

    private void addControls(View rootView) {
        txtUserName = rootView.findViewById(R.id.txtUserName);
        txtStudentId = rootView.findViewById(R.id.txtStudentId);
        btnCancel = rootView.findViewById(R.id.btnCancel);
        btnSignOut = rootView.findViewById(R.id.btnSignOut);
        imgAvatar = rootView.findViewById(R.id.imgAvatar);
    }

    private void addEvent () {
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEventPublisher().publish(Event.BtnSignOutCLicked);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void removeEvent () {

    }

    private void bind () {
        txtStudentId.setText(mUserInfo.getStudentId());
        txtUserName.setText(mUserInfo.getUserName());
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(getContext());
        Glide
                .with(this)
                .load(mUserInfo.getAvatarUrl())
                .centerCrop()
                .placeholder(circularProgressDrawable)
                .into(imgAvatar);
    }


    @Override
    public void onStart() {
        super.onStart();
        addEvent();
    }


    @Override
    public void onStop() {
        super.onStop();
        removeEvent();
    }

    private void showUserInfo () {

    }

}

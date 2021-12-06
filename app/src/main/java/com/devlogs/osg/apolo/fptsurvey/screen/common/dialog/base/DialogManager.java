package com.devlogs.osg.apolo.fptsurvey.screen.common.dialog.base;

import android.content.Context;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.devlogs.osg.apolo.fptsurvey.common.base.Observable;
import com.devlogs.osg.apolo.fptsurvey.screen.common.dialog.showinguserinfo.UserInfoDialogPM;
import com.devlogs.osg.apolo.fptsurvey.screen.common.dialog.showinguserinfo.UserInfoFragmentDialog;

import java.util.List;

public class DialogManager {
    private FragmentManager fragmentManager;
    private Context context;

    public DialogManager (Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
    }



    public Observable<DialogEventPublisher.Listener> showUserInfoDialog (UserInfoDialogPM userInfo) {
        List<Fragment> fragments = fragmentManager.getFragments();

        for (Fragment fragment : fragments) {
                if (DialogFragment.class.isAssignableFrom(fragment.getClass())) {
                ((DialogFragment) fragment).dismissAllowingStateLoss();
            }

        }
        UserInfoFragmentDialog mDialog = UserInfoFragmentDialog.newInstance(userInfo);
        mDialog.show(fragmentManager, UserInfoFragmentDialog.TAG);
        return mDialog;
    }

    public void dismissUserInfoFragmentDialog () {
        dismissFragment(UserInfoFragmentDialog.TAG);
    }

    private void dismissFragment (String tag) {
        Fragment fragment = fragmentManager.findFragmentByTag(tag);

        if (fragment != null) {
            if (fragment.isStateSaved()) {
                ((DialogFragment) fragment).dismissAllowingStateLoss();
            } else {
                ((DialogFragment) fragment).dismiss();
            }
        }
    }

}

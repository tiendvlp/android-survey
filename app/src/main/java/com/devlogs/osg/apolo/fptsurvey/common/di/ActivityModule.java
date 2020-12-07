package com.devlogs.osg.apolo.fptsurvey.common.di;

import android.app.Activity;
import android.text.Layout;
import android.view.LayoutInflater;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.devlogs.osg.apolo.fptsurvey.screen.common.MvcViewFactory;
import com.devlogs.osg.apolo.fptsurvey.screen.common.dialog.base.DialogManager;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.scopes.ActivityScoped;

@Module
@InstallIn(ActivityComponent.class)
public class ActivityModule {

    @Provides
    public LayoutInflater provideLayoutInflater (Activity activity) {
        return activity.getLayoutInflater();
    }

    @Provides
    public MvcViewFactory provideMvcViewFactory (LayoutInflater layoutInflater) {
        return new MvcViewFactory(layoutInflater);
    }

    @Provides
    public FragmentManager provideFragmentManager (Activity activity) {
        return ((FragmentActivity) activity).getSupportFragmentManager();
    }

    @Provides
    @ActivityScoped
    public DialogManager provideDialogManager (Activity activity, FragmentManager fragmentManager) {
        return new DialogManager(activity, fragmentManager);
    }
}

package com.devlogs.osg.apolo.fptsurvey.common.helper;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class ScreenDimensionSupport {

    public static final ScreenDimensionSupport instance = new ScreenDimensionSupport();

    public static class ScreenSize {
        private int width;
        private int height;

        public ScreenSize(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

    public ScreenSize getScreenSize (Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        return new ScreenSize(width, height);
    }

    public float convertDpToPx(Context context, float dp) {
       return dp * context.getResources().getDisplayMetrics().density;
    }

}

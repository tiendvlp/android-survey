package com.devlogs.osg.apolo.fptsurvey.screen.common.toolbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.devlogs.osg.apolo.fptsurvey.R;
import com.devlogs.osg.apolo.fptsurvey.screen.common.basemvcview.BaseMvcView;

public class HeaderToolbarMvcViewImp extends BaseMvcView<HeaderToolbarMvcView.Listener> implements HeaderToolbarMvcView  {
    private TextView txtHeader;
    private LinearLayout lnDivideLine;
    private boolean isDivideAlreadyFadeOut = false;
    private boolean isHeaderAlreadyFadeOut = false;

    public HeaderToolbarMvcViewImp (LayoutInflater inflater, ViewGroup viewGroup) {
        setRootView(inflater.inflate(R.layout.layout_header_toolbar, viewGroup, false));
        txtHeader = findViewById(R.id.txtToolbarTitle);
        lnDivideLine = findViewById(R.id.lnDivideLine);
        getRootView().setLayoutParams(new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) convertDpToPx(getContext(), 50)));
    }

    private float convertDpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    @Override
    public void setHeader(String header) {
        txtHeader.setText(header);
    }

    @Override
    public void headerFadeIn() {
        if (isHeaderAlreadyFadeOut) {
            txtHeader.animate().alpha(1f).setDuration(100);
            isHeaderAlreadyFadeOut = false;
        }
    }

    @Override
    public void headerFadeOut() {
        if (!isHeaderAlreadyFadeOut) {
            txtHeader.animate().alpha(0f).setDuration(100);
            isHeaderAlreadyFadeOut = true;
        }
    }

    @Override
    public void divideFadeIn() {
        if (isDivideAlreadyFadeOut) {
            lnDivideLine.animate().alpha(1f).setDuration(100);
            isDivideAlreadyFadeOut = false;
        }
    }

    @Override
    public void divideFadeOut() {
        if (!isDivideAlreadyFadeOut) {
            lnDivideLine.animate().alpha(0f).setDuration(100);
            isDivideAlreadyFadeOut = true;
        }
    }
}

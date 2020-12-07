package com.devlogs.osg.apolo.fptsurvey.screen.common.customview;

import android.content.Context;
import android.media.Image;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

class ImageButton extends androidx.appcompat.widget.AppCompatButton {
    private Animation fadeOutAnim = new AlphaAnimation(0.6f, 1.0f);
    private View.OnClickListener mOnClickListener= null;

    ImageButton (Context context) {
        super(context);
        fadeOutAnim.setDuration(200);
        fadeOutAnim.setFillAfter(true);
        setOnTouchListener(null);
    }

    ImageButton (Context context, AttributeSet attr) {
        super(context, attr);
        setOnTouchListener(null);
    }

    @Override
    public void setOnTouchListener(OnTouchListener listener) {
        OnTouchListener newOnTouchListener = new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    view.setAlpha(0.6f);
                    return true;
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    view.setAlpha(1.0f);
                    startAnimation(fadeOutAnim);
                    if (mOnClickListener != null) {
                        mOnClickListener.onClick(ImageButton.this);
                    }
                    return true;
                }

                if (listener != null) {
                    return listener.onTouch(view, motionEvent);
                }
                return false;
            }
        };
        super.setOnTouchListener(newOnTouchListener);
    }

    @Override
    public void setOnClickListener (View.OnClickListener listener) {
        this.mOnClickListener = listener;
    }
}

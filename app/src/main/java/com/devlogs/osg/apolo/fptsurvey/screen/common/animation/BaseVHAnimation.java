package com.devlogs.osg.apolo.fptsurvey.screen.common.animation;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.recyclerview.widget.RecyclerView;

public class BaseVHAnimation extends RecyclerView.ViewHolder {

        public float currentVelocity = 0f;

        /**
         * A [SpringAnimation] for this RecyclerView item. This animation rotates the view with a bouncy
         * spring configuration, resulting in the oscillation effect.
         * <p>
         * The animation is started in [Recyclerview.onScrollListener].
         */
        public SpringAnimation rotation = new SpringAnimation(itemView, SpringAnimation.ROTATION)
                .setSpring(
                        new SpringForce()
                                .setFinalPosition(0f)
                                .setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY)
                                .setStiffness(SpringForce.STIFFNESS_HIGH)
                )
                .addUpdateListener((a, b, velocity) -> {
                    currentVelocity = velocity;
                });

        /**
         * A [SpringAnimation] for this RecyclerView item. This animation is used to bring the item back
         * after the over-scroll effect.
         */
        public SpringAnimation translationY = new SpringAnimation(itemView, SpringAnimation.TRANSLATION_Y)
                .setSpring(
                        new SpringForce()
                                .setFinalPosition(0f)
                                .setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY)
                                .setStiffness(SpringForce.STIFFNESS_LOW)
                );

        public BaseVHAnimation(@NonNull View itemView) {
            super(itemView);
    }
}

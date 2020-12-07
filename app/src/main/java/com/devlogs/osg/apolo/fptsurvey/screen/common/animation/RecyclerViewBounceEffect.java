package com.devlogs.osg.apolo.fptsurvey.screen.common.animation;

import android.widget.EdgeEffect;

import androidx.recyclerview.widget.RecyclerView;

import com.devlogs.osg.apolo.fptsurvey.screen.surveyscreen.controller.QuestionRcvAdapter;

class RecyclerViewBounceEffect {
    /**
     * The magnitude of rotation while the list is scrolled.
     */
    public static final float SCROLL_ROTATION_MAGNITUDE = 0.25f;

    /**
     * The magnitude of rotation while the list is over-scrolled.
     */
    public static final int OVERSCROLL_ROTATION_MAGNITUDE = -10;

    /**
     * The magnitude of translation distance while the list is over-scrolled.
     */
    public static final float OVERSCROLL_TRANSLATION_MAGNITUDE = 0.2f;

    /**
     * The magnitude of translation distance when the list reaches the edge on fling.
     */
    public static final float FLING_TRANSLATION_MAGNITUDE = 0.5f;

    private RecyclerView.EdgeEffectFactory edgeEffectFactory = new RecyclerView.EdgeEffectFactory() {
        public EdgeEffect createEdgeEffect(RecyclerView recyclerView, int direction) {
            return new EdgeEffect(recyclerView.getContext()) {

                public void onPull(Float deltaDistance) {
                    super.onPull(deltaDistance);
                    handlePull(deltaDistance);
                }

                public void onPull(float deltaDistance, float displacement) {
                    super.onPull(deltaDistance, displacement);
                }

                private void handlePull(float deltaDistance) {
                    // This is called on every touch event while the list is scrolled with a finger.
                    // We simply update the view properties without animation.
                    int sign;
                    if (direction == DIRECTION_BOTTOM) {
                        sign = -1;
                    } else {
                        sign = 1;
                    }
                    float rotationDelta = sign * deltaDistance * OVERSCROLL_ROTATION_MAGNITUDE;
                    float translationYDelta = sign * recyclerView.getWidth() * deltaDistance * OVERSCROLL_TRANSLATION_MAGNITUDE;
                    for (int i = 0; i <= recyclerView.getChildCount(); ++i) {
                        if (recyclerView.getChildAt(i) == null) {continue;}
                        BaseVHAnimation holder = (BaseVHAnimation) recyclerView.getChildViewHolder(recyclerView.getChildAt(i));
                        holder.rotation.cancel();
                        holder.translationY.cancel();
                        holder.itemView.setRotation(holder.itemView.getRotation() + rotationDelta);
                        holder.itemView.setTranslationX(holder.itemView.getTranslationY() + translationYDelta);
                    }
                }

                public void onRelease() {
                    super.onRelease();
                    // The finger is lifted. This is when we should start the animations to bring
                    // the view property values back to their resting states.
                    for (int i = 0; i <= recyclerView.getChildCount(); ++i) {
                        if (recyclerView.getChildAt(i) == null) {continue;}
                        BaseVHAnimation holder = (BaseVHAnimation) recyclerView.getChildViewHolder(recyclerView.getChildAt(i));
                        holder.rotation.start();
                        holder.translationY.start();
                    }
                }

                public void onAbsorb(int velocity) {
                    super.onAbsorb(velocity);
                    int sign;
                    if (direction == DIRECTION_BOTTOM) {
                        sign = -1;
                    } else {
                        sign = 1;
                    }                    // The list has reached the edge on fling.
                    float translationVelocity = sign * velocity * FLING_TRANSLATION_MAGNITUDE;
                    for (int i = 0; i <= recyclerView.getChildCount(); ++i) {
                        if (recyclerView.getChildAt(i) == null) {continue;}
                        BaseVHAnimation holder = (BaseVHAnimation) recyclerView.getChildViewHolder(recyclerView.getChildAt(i));
                        holder.translationY
                                .setStartVelocity(translationVelocity)
                                .start();
                    }
                }
            };
        }
    };
    public RecyclerView.EdgeEffectFactory getBounceEffect () {
        return this.edgeEffectFactory;
    }
}

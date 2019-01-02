package com.ja.getdevicelocation;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * author: Jiaying Guo
 * Swipe Gesture Listener class implements the onTouchListener interface and inherits SimpleOnGestureListener in its private
 * class to redefine the gesture of swiping horizontally and vertically.
 */
public class OnSwipeTouchListener implements OnTouchListener {

    private final GestureDetector gestureDetector;

    /**
     * Constructor initialize a gesture detector object with the input context class, in which the swipe is performed.
     * @param ctx
     */
    public OnSwipeTouchListener (Context ctx){
        gestureDetector = new GestureDetector(ctx, new GestureListener());
    }

    /**
     * Take in a view and motion event to detect whether there's a touch event on this view
     * @param view
     * @param event
     * @return true of touch event occurs, else false
     */
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    /**
     * Private class to define the swipe gestures, including swipeLeft/Right/Top/Bottom
     */
    private final class GestureListener extends SimpleOnGestureListener {

        // Define constants of swipe gesture threshold for later detection
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;


        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        /**
         * Take in the starting point e1 and end point e2 of the gesture, and its velocities (pixel per sec) in X and Y axis.
         * If the difference between starting point's and end point's X or Y coordinates is larger than SWIPE_THRESHOLD, and
         * X velocity or Y velocity is larger than SWIPE_VELOCITY_THRESHOLD, then a swipe gesture horizontally or vertically is detected.
         * @param e1
         * @param e2
         * @param velocityX
         * @param velocityY
         * @return true if the activity is consumed, else false.
         */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                        result = true;
                    }
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                    result = true;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    /**
     *  Provide access to redefine the behavior after swiping right
     */
    public void onSwipeRight() {
    }

    /**
     *  Provide access to redefine the behavior after swiping left
     */
    public void onSwipeLeft() {
    }

    /**
     *  Provide access to redefine the behavior after swiping top
     */
    public void onSwipeTop() {
    }

    /**
     *  Provide access to redefine the behavior after swiping bottom
     */
    public void onSwipeBottom() {
    }
}
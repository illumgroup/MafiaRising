package com.illum.MafiaRising;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class BidirectionalViewPager extends ViewPager {

    String swipeDirection;

    public BidirectionalViewPager(Context context) {
        super(context);
        init();
    }

    public BidirectionalViewPager(Context context, AttributeSet attribs) {
        super(context, attribs);

        TypedArray attribArray = context.obtainStyledAttributes(
                attribs,
                R.styleable.BidirectionalViewPager);

        swipeDirection = attribArray.getString(R.styleable.BidirectionalViewPager_swipeDirection);

        attribArray.recycle();

        init();
    }

    private void init() {
        setPageTransformer(true, new BidirectionalPageTransformer());
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    private class BidirectionalPageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View view, float position) {
            if(position < -1) {
                view.setAlpha(0);
            }
            else if (position <= 1) {
                view.setAlpha(1);
                if(swipeDirection.equals("vertical")) {
                    view.setTranslationX(view.getWidth() * -position);
                    float yPos = position * view.getHeight();
                    view.setTranslationY(yPos);
                }
                else {
                    //do regular transform
                    //? how does it know?
                }
            }
            else if (position > 1) {
                view.setAlpha(0);
            }
        }
    }

    private MotionEvent swapXY(MotionEvent evt) {
        float width = getWidth();
        float height = getHeight();

        float finalX = (evt.getY() / height) * width;
        float finalY = (evt.getX() / width) * height;

        evt.setLocation(finalX, finalY);

        return evt;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent evt) {
        if(swipeDirection.equals("vertical")) {
            boolean intercepted = super.onInterceptTouchEvent(swapXY(evt));
            swapXY(evt);
            return intercepted;
        }
        else {
            return super.onInterceptTouchEvent(evt);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent evt) {
        Log.i("INFO","Hello\n"+evt.toString());
        if(swipeDirection.equals("vertical")) {
            return super.onTouchEvent(swapXY(evt));
        }
        else {
            return super.onTouchEvent(evt);
        }
    }

}

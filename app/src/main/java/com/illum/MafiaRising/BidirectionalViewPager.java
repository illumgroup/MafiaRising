package com.illum.MafiaRising;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class BidirectionalViewPager extends ViewPager {

    String pagerSwipeDirection; //can be overriden by pageSwipeDirection of BidirectionalViewPage
    static String swipeHorizontalString;
    static String swipeVerticalString;

    public BidirectionalViewPager(Context context) {
        super(context);
        init();
    }

    public BidirectionalViewPager(Context context, AttributeSet attribs) {
        super(context, attribs);

        swipeHorizontalString = getResources().getString(R.string.swipe_direction_horizontal);
        swipeVerticalString = getResources().getString(R.string.swipe_direction_vertical);

        TypedArray attribArray = context.obtainStyledAttributes(
                attribs,
                R.styleable.BidirectionalViewPager);

        pagerSwipeDirection = attribArray.getString(R.styleable.BidirectionalViewPager_pagerSwipeDirection);
        if (pagerSwipeDirection == null) {
            pagerSwipeDirection = swipeHorizontalString;
        }

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
            if(position < -1 || position > 1) {
                view.setAlpha(0);
            }
            else {
                view.setAlpha(1);
                int i = indexOfChild(view);
                if (position < 0) {
                    if(i > 1) {
                        i--;
                    }
                    //Log.i("INFO", "L: "+Integer.toString(i));
                    BidirectionalViewPage curPage = (BidirectionalViewPage) getChildAt(i);
                    String pageSwipeDirection = curPage.getPageSwipeDirection();
                    if((pageSwipeDirection != null && pageSwipeDirection.equals(swipeVerticalString)) || pagerSwipeDirection.equals(swipeVerticalString)) {
                        view.setTranslationX(view.getWidth() * -position);
                        float yPos = position * view.getHeight();
                        view.setTranslationY(yPos);
                    }
                }
                else {
                    if(i > 1) {
                        i--;
                    }
                    //Log.i("INFO", "R: "+Integer.toString(i));
                    BidirectionalViewPage curPage = (BidirectionalViewPage) getChildAt(i);
                    String pageSwipeDirection = curPage.getPageSwipeDirection();
                    if((pageSwipeDirection != null && pageSwipeDirection.equals(swipeVerticalString)) || pagerSwipeDirection.equals(swipeVerticalString)) {
                        view.setTranslationX(view.getWidth() * -position);
                        float yPos = position * view.getHeight();
                        view.setTranslationY(yPos);
                    }
                }
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

    /*@Override
    public boolean onInterceptTouchEvent(MotionEvent evt) {
        if(pagerSwipeDirection.equals(swipeVerticalString)) {
            boolean intercepted = super.onInterceptTouchEvent(swapXY(evt));
            swapXY(evt);
            return intercepted;
        }
        else {
            return super.onInterceptTouchEvent(evt);
        }
    }*/

    /*public boolean onTouchChildEvent(MotionEvent evt, View view) {
        BidirectionalViewPage p = (BidirectionalViewPage) view;

        String pageSwipeDirection = p.getPageSwipeDirection();
        if((pageSwipeDirection != null && pageSwipeDirection.equals(swipeVerticalString)) || pagerSwipeDirection.equals(swipeVerticalString)) {
            return super.onTouchEvent(swapXY(evt));
        }
        else {
            return super.onTouchEvent(evt);
        }
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent evt) {
        int x = Math.round(evt.getX());
        int y = Math.round(evt.getY());
        int numChildren = getChildCount();
        for(int i=0;i<numChildren;++i) {
            View child = getChildAt(i);
            Rect bb = new Rect();
            child.getGlobalVisibleRect(bb);
            if(bb.contains(x,y)) {
                BidirectionalViewPage curPage = (BidirectionalViewPage) child;

                String pageSwipeDirection = curPage.getPageSwipeDirection();
                if((pageSwipeDirection != null && pageSwipeDirection.equals(swipeVerticalString)) || pagerSwipeDirection.equals(swipeVerticalString)) {
                    return super.onTouchEvent(swapXY(evt));
                }
                else {
                    return super.onTouchEvent(evt);
                }
            }
        }
        return super.onTouchEvent(evt);
    }
}

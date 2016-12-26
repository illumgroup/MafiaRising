package com.illum.MafiaRising;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class BidirectionalViewPage extends LinearLayout {

    String pageSwipeDirection;
    String swipeHorizontalString;
    String swipeVerticalString;

    public BidirectionalViewPage(Context context) {
        super(context);
        init();
    }

    public BidirectionalViewPage(Context context, AttributeSet attribs) {
        super(context, attribs);

        TypedArray attribArray = context.obtainStyledAttributes(
                attribs,
                R.styleable.BidirectionalViewPage);

        pageSwipeDirection = attribArray.getString(R.styleable.BidirectionalViewPage_pageSwipeDirection);

        attribArray.recycle();

        init();
    }

    public void init() {
        swipeHorizontalString = getResources().getString(R.string.swipe_direction_horizontal);
        swipeVerticalString = getResources().getString(R.string.swipe_direction_vertical);

        if(pageSwipeDirection == null) {
            pageSwipeDirection = "";
        }
    }

    public String getPageSwipeDirection() {
        return pageSwipeDirection;
    }

    public void setPageSwipeDirection(String swipeDirection) {
        if (swipeDirection.equalsIgnoreCase(swipeHorizontalString) || swipeDirection.equalsIgnoreCase(swipeVerticalString)) {
            pageSwipeDirection = swipeDirection;
        }
    }
}

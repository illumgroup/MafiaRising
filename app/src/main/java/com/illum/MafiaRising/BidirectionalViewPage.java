package com.illum.MafiaRising;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

//class for a page of BidirectionalViewPager
//one public variable:
// pageSwipeDirection
public class BidirectionalViewPage extends LinearLayout {

    //swipe direction for this page, can be set in an attribute
    String pageSwipeDirection;
    //static string constants, I think I did this wrong
    static String swipeHorizontalString;
    static String swipeVerticalString;

    public BidirectionalViewPage(Context context) {
        super(context);
        init();
    }

    public BidirectionalViewPage(Context context, AttributeSet attribs) {
        super(context, attribs);

        //get default page swipe direction from attribute
        TypedArray attribArray = context.obtainStyledAttributes(
                attribs,
                R.styleable.BidirectionalViewPage);

        pageSwipeDirection = attribArray.getString(R.styleable.BidirectionalViewPage_pageSwipeDirection);

        attribArray.recycle();

        init();
    }

    //sets static string constants and checks default swipe direction
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

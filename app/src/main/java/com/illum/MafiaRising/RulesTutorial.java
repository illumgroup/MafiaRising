package com.illum.MafiaRising;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.regex.Pattern;

//shows tutorial viewpager screens
public class RulesTutorial extends BaseViewPagerActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_tutorial);

        slideLayoutID = R.layout.fragment_tutorial_slide;
        pagerViewID = R.id.pager;
        pageViewID = R.id.page;
        titlesResID = R.array.tutorial_slide_titles;
        swipeDirResID = R.array.tutorial_slide_swipedir;
        contentResID = R.array.tutorial_slide_content;
        swipeDirDefaultResID = R.string.tutorial_slide_swipedir_default;

        init();

        adapter = new PagerAdapter(getSupportFragmentManager());
        pager = (BidirectionalViewPager) findViewById(pagerViewID);
        pager.setAdapter(adapter);
        pager.setOnSwipeOutBoundsListener(new BidirectionalViewPager.OnSwipeOutBoundsListener() {
            @Override
            public void onSwipeOutBoundsAtStart() {
                RulesTutorial.super.onBackPressed();
            }

            @Override
            public void onSwipeOutBoundsAtEnd() {
                RulesTutorial.super.onBackPressed();
            }
        });
    }
}

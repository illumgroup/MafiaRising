package com.illum.MafiaRising;

import android.os.Bundle;

//shows script viewpager screens
//TODO: comment RulesScript and RulesTutorial
//TODO: put and get delimiter from strings resource
public class RulesScript extends BaseViewPagerActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_script);

        slideLayoutID = R.layout.fragment_script_slide;
        pagerViewID = R.id.pager;
        pageViewID = R.id.page;
        titlesResID = R.array.script_slide_titles;
        swipeDirResID = R.array.script_slide_swipedir;
        contentResID = R.array.script_slide_content;
        swipeDirDefaultResID = R.string.script_slide_swipedir_default;

        init();

        adapter = new PagerAdapter(getSupportFragmentManager());
        pager = (BidirectionalViewPager) findViewById(pagerViewID);
        pager.setAdapter(adapter);
        pager.setOnSwipeOutBoundsListener(new BidirectionalViewPager.OnSwipeOutBoundsListener() {
            @Override
            public void onSwipeOutBoundsAtStart() {
                RulesScript.super.onBackPressed();
            }

            @Override
            public void onSwipeOutBoundsAtEnd() {
                RulesScript.super.onBackPressed();
            }
        });
    }
}

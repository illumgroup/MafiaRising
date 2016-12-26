package com.illum.MafiaRising;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RulesTutorial extends FragmentActivity {
    RulesTutorialPagerAdapter adapter;
    BidirectionalViewPager pager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        adapter = new RulesTutorialPagerAdapter(getSupportFragmentManager());
        pager = (BidirectionalViewPager) findViewById(R.id.tutorial_pager);
        pager.setAdapter(adapter);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }

    public class RulesTutorialPagerAdapter extends FragmentStatePagerAdapter {
        RulesTutorialPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new TutorialSlide();
            Bundle args = new Bundle();
            args.putInt(TutorialSlide.SLIDE_NUM, i);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            int tutorialTitlesLen = getResources().getStringArray(R.array.tutorial_slide_titles).length;
            int tutorialSwipeDirsLen = getResources().getStringArray(R.array.tutorial_slide_swipedir).length;
            int tutorialContentLen = getResources().getStringArray(R.array.tutorial_slide_content).length;
            int len = tutorialTitlesLen;
            int[] lengths = {tutorialTitlesLen,tutorialSwipeDirsLen};
            for (int i: lengths) {
                if(i < len) len = i;
            }
            return len;
        }
    }

    public static class TutorialSlide extends Fragment {
        public static final String SLIDE_NUM = "#";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            String[] tutorialTitles = getResources().getStringArray(R.array.tutorial_slide_titles);
            String[] tutorialSwipeDirs = getResources().getStringArray(R.array.tutorial_slide_swipedir);
            String[] tutorialContent = getResources().getStringArray(R.array.tutorial_slide_content);

            View rootView = inflater.inflate(R.layout.fragment_tutorial_slide, container, false);
            Bundle args = getArguments();
            int i = args.getInt(SLIDE_NUM);

            BidirectionalViewPage slide = (BidirectionalViewPage) rootView.findViewById(R.id.tutorial_page);
            slide.setPageSwipeDirection(tutorialSwipeDirs[i]);

            TextView slideTitle = (TextView) slide.findViewById(R.id.header).findViewById(R.id.title);
            slideTitle.setText(tutorialTitles[i]);

            //content

            return rootView;
        }
    }
}

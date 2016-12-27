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
import android.view.ViewStub;
import android.widget.TextView;

import java.util.regex.Pattern;

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

        int tutorialTitlesLen;
        int tutorialContentLen;

        RulesTutorialPagerAdapter(FragmentManager fm) {
            super(fm);
            tutorialTitlesLen = getResources().getStringArray(R.array.tutorial_slide_titles).length;
            tutorialContentLen = getResources().getStringArray(R.array.tutorial_slide_content).length;
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
            int len = tutorialTitlesLen;
            int[] lengths = {tutorialTitlesLen,3};//,tutorialContentLen};
            for (int i: lengths) {
                if(i < len) len = i;
            }
            return len;
        }
    }

    public static class TutorialSlide extends Fragment {
        public static final String SLIDE_NUM = "#";
        String defaultPageSwipeDirection;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            String[] tutorialTitles = getResources().getStringArray(R.array.tutorial_slide_titles);
            String[] tutorialSwipeDirs = getResources().getStringArray(R.array.tutorial_slide_swipedir);
            String[] tutorialContent = getResources().getStringArray(R.array.tutorial_slide_content);

            if(tutorialSwipeDirs.length == 0) {
                defaultPageSwipeDirection = getResources().getString(R.string.tutorial_slide_swipedir_default);
            }

            View rootView = inflater.inflate(R.layout.fragment_tutorial_slide, container, false);
            Bundle args = getArguments();
            int curSlide = args.getInt(SLIDE_NUM);

            BidirectionalViewPage slide = (BidirectionalViewPage) rootView.findViewById(R.id.tutorial_page);
            if(defaultPageSwipeDirection != null) {
                slide.setPageSwipeDirection(defaultPageSwipeDirection);
            }
            else {
                slide.setPageSwipeDirection(tutorialSwipeDirs[curSlide]);
            }

            TextView slideTitle = (TextView) slide.findViewById(R.id.header).findViewById(R.id.title);
            slideTitle.setText(tutorialTitles[curSlide]);

            //3 content layout types
            //1. 1 textview, centered vertically, auto-scale text or include attribs in strings
            //2. 2 textviews, linearlayout, 1/2 n 1/2, can change text color
            //3. 1 drawable, 1 textview, linearlayout, 7/10
            int contentLayout = 1;
            String text = "a", textSize = "", textColor = "", font = "", gravity = "";
            String[] contentAttribsUnparsed = tutorialContent[curSlide].split(Pattern.quote("`|"));
            Log.i("INFO","START "+Integer.toString(curSlide));
            for (String contentAttribUnparsed: contentAttribsUnparsed) {
                Log.i("INFO","unparsed: "+contentAttribUnparsed);
                String[] contentAttrib = contentAttribUnparsed.split(Pattern.quote(":"));
                Log.i("INFO","attrib0: "+contentAttrib[0]);
                Log.i("INFO","attrib1: "+contentAttrib[1]);
                switch (contentAttrib[0]) {
                    case "layout":
                        contentLayout = Integer.parseInt(contentAttrib[1]);
                        break;
                    case "text":
                        text = contentAttrib[1];
                        break;
                    case "textSize":
                        textSize = contentAttrib[1];
                        break;
                    case "textColor":
                        textColor = contentAttrib[1];
                        break;
                    case "font":
                        font = contentAttrib[1];
                        break;
                    case "gravity":
                        gravity = contentAttrib[1];
                        break;
                }
                contentAttrib = new String[] {};
            }
            Log.i("INFO","text: "+text);
            ViewGroup contentStub = (ViewGroup) slide.findViewById(R.id.content);
            //ViewStub contentStub = (ViewStub) slide.findViewById(R.id.content);
            View content = null;
            if(contentLayout == 1) {
                content = inflater.inflate(R.layout.fragment_tutorial_content_1,contentStub,false);
                //contentStub.setLayoutResource(R.layout.fragment_tutorial_content_1);
                //contentStub.inflate();
                //View content = (View) contentStub.findViewById(R.id.content_layout);
                Log.i("INFO","contentclass: "+content.getClass());
                Log.i("INFO","textviewclass: "+content.findViewById(R.id.text_1).getClass());
                TextView contentText1 = (TextView) content.findViewById(R.id.text_1);
                Log.i("INFO","textview: "+contentText1);
                contentText1.setText(text);
            }
            /*else if(contentLayout == 2) {
                content.setLayoutResource(R.layout.fragment_tutorial_content_2);
            }
            else if(contentLayout == 3) {
                content.setLayoutResource(R.layout.fragment_tutorial_content_3);
            }*/

            contentStub.addView(content);

            return rootView;
        }
    }
}

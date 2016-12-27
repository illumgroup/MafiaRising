package com.illum.MafiaRising;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
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

import org.w3c.dom.Text;

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
            int[] lengths = {tutorialTitlesLen,tutorialContentLen};
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
            Context context = rootView.getContext();
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
            String text1 = "", text1Size = "0sp", text1Color = "#FFFFFF", text1font = getResources().getString(R.string.font_name_kefa), text1gravity = "center";
            String text2 = "", text2Size = "0sp", text2Color = "#FFFFFF", text2font = getResources().getString(R.string.font_name_kefa), text2gravity = "center";
            String[] contentAttribsUnparsed = tutorialContent[curSlide].split(Pattern.quote("`|"));
            Log.i("INFO","START "+Integer.toString(curSlide));
            for (String contentAttribUnparsed: contentAttribsUnparsed) {
                //Log.i("INFO","unparsed: "+contentAttribUnparsed);
                String[] contentAttrib = contentAttribUnparsed.split(Pattern.quote(":"),2);
                //Log.i("INFO","attrib0: "+contentAttrib[0]);
                //Log.i("INFO","attrib1: "+contentAttrib[1]);
                switch (contentAttrib[0]) {
                    case "layout":
                        contentLayout = Integer.parseInt(contentAttrib[1]);
                        break;
                    case "text1":
                        text1 = contentAttrib[1];
                        break;
                    case "text1Size":
                        text1Size = contentAttrib[1];
                        break;
                    case "text1Color":
                        text1Color = contentAttrib[1];
                        break;
                    case "text1font":
                        text1font = contentAttrib[1];
                        break;
                    case "text1gravity":
                        text1gravity = contentAttrib[1];
                        break;
                    case "text2":
                        text2 = contentAttrib[1];
                        break;
                    case "text2Size":
                        text2Size = contentAttrib[1];
                        break;
                    case "text2Color":
                        text2Color = contentAttrib[1];
                        break;
                    case "text2font":
                        text2font = contentAttrib[1];
                        break;
                    case "text2gravity":
                        text2gravity = contentAttrib[1];
                        break;
                }
            }

            ViewGroup contentStub = (ViewGroup) slide.findViewById(R.id.content);
            View content = null;
            if(contentLayout == 1) {
                content = inflater.inflate(R.layout.fragment_tutorial_content_1,contentStub,false);

                CustomFontTextView contentText1 = (CustomFontTextView) content.findViewById(R.id.text_1);
                contentText1.setText(text1);
                contentText1.setTextSize(Integer.parseInt(text1Size));
                contentText1.setTextColor(Color.parseColor(getResources().getString(getResources().getIdentifier(text1Color,"color",context.getPackageName()))));
                contentText1.setGravity(getGravity(text1gravity));
                contentText1.setFont(text1font);
            }
            else if(contentLayout == 2) {
                content = inflater.inflate(R.layout.fragment_tutorial_content_2,contentStub,false);

                CustomFontTextView contentText1 = (CustomFontTextView) content.findViewById(R.id.text_1);
                contentText1.setText(text1);
                contentText1.setTextSize(Integer.parseInt(text1Size));
                contentText1.setTextColor(Color.parseColor(getResources().getString(getResources().getIdentifier(text1Color,"color",context.getPackageName()))));
                contentText1.setGravity(getGravity(text1gravity));
                contentText1.setFont(text1font);

                CustomFontTextView contentText2 = (CustomFontTextView) content.findViewById(R.id.text_2);
                contentText2.setText(text2);
                contentText2.setTextSize(Integer.parseInt(text2Size));
                contentText2.setTextColor(Color.parseColor(getResources().getString(getResources().getIdentifier(text2Color,"color",context.getPackageName()))));
                contentText2.setGravity(getGravity(text2gravity));
                contentText2.setFont(text2font);
            }
            else if(contentLayout == 3) {
                content = inflater.inflate(R.layout.fragment_tutorial_content_3,contentStub,false);

                ImageView contentImg1 = (ImageView) content.findViewById(R.id.content_img).findViewById(R.id.img_1);

                CustomFontTextView contentText1 = (CustomFontTextView) content.findViewById(R.id.text_1);
                contentText1.setText(text1);
                contentText1.setTextSize(Integer.parseInt(text1Size));
                contentText1.setTextColor(Color.parseColor(getResources().getString(getResources().getIdentifier(text1Color,"color",context.getPackageName()))));
                contentText1.setGravity(getGravity(text1gravity));
                contentText1.setFont(text1font);
            }

            contentStub.addView(content);

            return rootView;
        }
    }

    public static int getGravity(String gravity) {
        switch(gravity) {
            case "top":
                return 0x30;
            case "bottom":
                return 0x50;
            case "left":
                return 0x03;
            case "right":
                return 0x05;
            case "center_vertical":
                return 0x10;
            case "fill_vertical":
                return 0x70;
            case "center_horizontal":
                return 0x01;
            case "fill_horizontal":
                return 0x07;
            case "center":
                return 0x11;
            case "fill":
                return 0x77;
            case "clip_vertical":
                return 0x80;
            case "clip_horizontal":
                return 0x08;
            case "start":
                return 0x00800003;
            case "end":
                return 0x00800005;
            default:
                return 0x30;
        }
    }
}

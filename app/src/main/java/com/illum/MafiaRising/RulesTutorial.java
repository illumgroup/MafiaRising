package com.illum.MafiaRising;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        adapter = new RulesTutorialPagerAdapter(getSupportFragmentManager());
        pager = (BidirectionalViewPager) findViewById(R.id.tutorial_pager);
        pager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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

            int contentLayout = 1;
            String contentLayoutColor = "#FFFFFF";
            String text1 = "", text1Size = "0", text1Color = "#FFFFFF", text1Font = getResources().getString(R.string.font_name_kefa), text1Gravity = "center_vertical";
            String text2 = "", text2Size = "0", text2Color = "#FFFFFF", text2Font = getResources().getString(R.string.font_name_kefa), text2Gravity = "center_vertical";
            String img1 = "placeholderbox", img1MaxWidth = "", img1MinWidth = "", img1MaxHeight = "", img1MinHeight = "", img1ScaleType = "";
            String[] contentAttribsUnparsed = tutorialContent[curSlide].split(Pattern.quote("`|"));
            for (String contentAttribUnparsed: contentAttribsUnparsed) {
                String[] contentAttrib = contentAttribUnparsed.split(Pattern.quote(":"),2);
                switch (contentAttrib[0]) {
                    case "layout":
                        contentLayout = Integer.parseInt(contentAttrib[1]);
                        break;
                    case "layoutColor":
                        contentLayoutColor = contentAttrib[1];
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
                    case "text1Font":
                        text1Font = contentAttrib[1];
                        break;
                    case "text1Gravity":
                        text1Gravity = contentAttrib[1];
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
                    case "text2Font":
                        text2Font = contentAttrib[1];
                        break;
                    case "text2Gravity":
                        text2Gravity = contentAttrib[1];
                        break;
                    case "img1":
                        img1 = contentAttrib[1];
                        break;
                    case "img1MaxWidth":
                        img1MaxWidth = contentAttrib[1];
                        break;
                    case "img1MinWidth":
                        img1MinWidth = contentAttrib[1];
                        break;
                    case "img1MaxHeight":
                        img1MaxHeight = contentAttrib[1];
                        break;
                    case "img1MinHeight":
                        img1MinHeight = contentAttrib[1];
                        break;
                    case "img1ScaleType":
                        img1ScaleType = contentAttrib[1];
                        break;
                }
            }

            ViewGroup contentStub = (ViewGroup) slide.findViewById(R.id.content);
            View content = null;
            if(contentLayout == 1) {
                content = inflater.inflate(R.layout.fragment_content_layout_1,contentStub,false);
                content.setBackgroundColor(Color.parseColor(getResources().getString(getResources().getIdentifier(contentLayoutColor,"color",context.getPackageName()))));

                CustomFontTextView contentText1 = (CustomFontTextView) content.findViewById(R.id.text_1);
                contentText1.setText(text1);
                contentText1.setTextSize(Integer.parseInt(text1Size));
                contentText1.setTextColor(Color.parseColor(getResources().getString(getResources().getIdentifier(text1Color,"color",context.getPackageName()))));
                contentText1.setGravity(EnumUtils.getGravity(text1Gravity));
                contentText1.setFont(text1Font);
            }
            else if(contentLayout == 2) {
                content = inflater.inflate(R.layout.fragment_content_layout_2,contentStub,false);
                content.setBackgroundColor(Color.parseColor(getResources().getString(getResources().getIdentifier(contentLayoutColor,"color",context.getPackageName()))));

                CustomFontTextView contentText1 = (CustomFontTextView) content.findViewById(R.id.text_1);
                contentText1.setText(text1);
                contentText1.setTextSize(Integer.parseInt(text1Size));
                contentText1.setTextColor(Color.parseColor(getResources().getString(getResources().getIdentifier(text1Color,"color",context.getPackageName()))));
                contentText1.setGravity(EnumUtils.getGravity(text1Gravity));
                contentText1.setFont(text1Font);

                CustomFontTextView contentText2 = (CustomFontTextView) content.findViewById(R.id.text_2);
                contentText2.setText(text2);
                contentText2.setTextSize(Integer.parseInt(text2Size));
                contentText2.setTextColor(Color.parseColor(getResources().getString(getResources().getIdentifier(text2Color,"color",context.getPackageName()))));
                contentText2.setGravity(EnumUtils.getGravity(text2Gravity));
                contentText2.setFont(text2Font);
            }
            else if(contentLayout == 3) {
                content = inflater.inflate(R.layout.fragment_content_layout_3,contentStub,false);
                content.setBackgroundColor(Color.parseColor(getResources().getString(getResources().getIdentifier(contentLayoutColor,"color",context.getPackageName()))));

                ImageView contentImg1 = (ImageView) content.findViewById(R.id.content_img).findViewById(R.id.img_1);
                contentImg1.setImageDrawable(ContextCompat.getDrawable(context,getResources().getIdentifier(img1,"drawable",context.getPackageName())));
                final float scale = context.getResources().getDisplayMetrics().density;
                if(!img1MaxWidth.isEmpty()) { contentImg1.setMaxWidth((int)(Integer.parseInt(img1MaxWidth)*scale+0.5f)); }
                if(!img1MinWidth.isEmpty()) { contentImg1.setMinimumWidth((int)(Integer.parseInt(img1MinWidth)*scale+0.5f)); }
                if(!img1MaxHeight.isEmpty()) { contentImg1.setMaxHeight((int)(Integer.parseInt(img1MaxHeight)*scale+0.5f)); }
                if(!img1MinHeight.isEmpty()) { contentImg1.setMinimumHeight((int)(Integer.parseInt(img1MinHeight)*scale+0.5f)); }
                if(!img1ScaleType.isEmpty()) { contentImg1.setScaleType(EnumUtils.getScaleType(img1ScaleType)); }

                CustomFontTextView contentText1 = (CustomFontTextView) content.findViewById(R.id.text_1);
                contentText1.setText(text1);
                contentText1.setTextSize(Integer.parseInt(text1Size));
                contentText1.setTextColor(Color.parseColor(getResources().getString(getResources().getIdentifier(text1Color,"color",context.getPackageName()))));
                contentText1.setGravity(EnumUtils.getGravity(text1Gravity));
                contentText1.setFont(text1Font);
            }

            contentStub.addView(content);

            return rootView;
        }
    }
}

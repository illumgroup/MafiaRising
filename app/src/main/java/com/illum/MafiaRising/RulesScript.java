package com.illum.MafiaRising;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.regex.Pattern;

//shows script viewpager screens
//TODO: make BaseFragmentActivity that generalizes common functions
//TODO: comment RulesScript and RulesTutorial
public class RulesScript extends FragmentActivity {
    RulesScriptPagerAdapter adapter;
    BidirectionalViewPager pager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_script);

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

        adapter = new RulesScriptPagerAdapter(getSupportFragmentManager());
        pager = (BidirectionalViewPager) findViewById(R.id.script_pager);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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

    //main part that fills the activity
    public class RulesScriptPagerAdapter extends FragmentStatePagerAdapter {

        int scriptTitlesLen;
        int scriptContentLen;

        RulesScriptPagerAdapter(FragmentManager fm) {
            super(fm);
            scriptTitlesLen = getResources().getStringArray(R.array.script_slide_titles).length;
            scriptContentLen = getResources().getStringArray(R.array.script_slide_content).length;
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new ScriptSlide();
            Bundle args = new Bundle();
            args.putInt(ScriptSlide.SLIDE_NUM, i);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            int len = scriptTitlesLen;
            int[] lengths = {scriptTitlesLen,scriptContentLen};
            for (int i: lengths) {
                if(i < len) len = i;
            }
            return len;
        }
    }

    public static class ScriptSlide extends Fragment {
        public static final String SLIDE_NUM = "#";
        String defaultPageSwipeDirection;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            //get slide info
            String[] scriptTitles = getResources().getStringArray(R.array.script_slide_titles);
            String[] scriptSwipeDirs = getResources().getStringArray(R.array.script_slide_swipedir);
            String[] scriptContent = getResources().getStringArray(R.array.script_slide_content);

            if(scriptSwipeDirs.length == 0) {
                defaultPageSwipeDirection = getResources().getString(R.string.script_slide_swipedir_default);
            }

            View rootView = inflater.inflate(R.layout.fragment_script_slide, container, false);
            Context context = rootView.getContext();
            Bundle args = getArguments();
            int curSlide = args.getInt(SLIDE_NUM);

            BidirectionalViewPage slide = (BidirectionalViewPage) rootView.findViewById(R.id.script_page);
            if(defaultPageSwipeDirection != null) {
                slide.setPageSwipeDirection(defaultPageSwipeDirection);
            }
            else {
                slide.setPageSwipeDirection(scriptSwipeDirs[curSlide]);
            }

            //fill page contents
            TextView slideTitle = (TextView) slide.findViewById(R.id.header).findViewById(R.id.title);
            slideTitle.setText(scriptTitles[curSlide]);

            int contentLayout = 1;
            String contentLayoutColor = "#FFFFFF";
            String text1 = "", text1Size = "0", text1Color = "#FFFFFF", text1Font = getResources().getString(R.string.font_name_manteka), text1Gravity = "center_vertical";
            String text2 = "", text2Size = "0", text2Color = "#FFFFFF", text2Font = getResources().getString(R.string.font_name_manteka), text2Gravity = "center_vertical";
            String img1 = "placeholderbox", img1MaxWidth = "", img1MinWidth = "", img1MaxHeight = "", img1MinHeight = "", img1ScaleType = "";
            String[] contentAttribsUnparsed = scriptContent[curSlide].split(Pattern.quote("`|"));
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

                final CustomFontTextView contentText1 = (CustomFontTextView) content.findViewById(R.id.text_1);
                contentText1.setText(text1);
                contentText1.setTextSize(Integer.parseInt(text1Size));
                contentText1.setTextColor(Color.parseColor(getResources().getString(getResources().getIdentifier(text1Color,"color",context.getPackageName()))));
                contentText1.setGravity(EnumUtils.getGravity(text1Gravity));
                contentText1.setFont(text1Font);
            }
            else if(contentLayout == 2) {
                content = inflater.inflate(R.layout.fragment_content_layout_2,contentStub,false);
                content.setBackgroundColor(Color.parseColor(getResources().getString(getResources().getIdentifier(contentLayoutColor,"color",context.getPackageName()))));

                final CustomFontTextView contentText1 = (CustomFontTextView) content.findViewById(R.id.text_1);
                contentText1.setText(text1);
                contentText1.setTextSize(Integer.parseInt(text1Size));
                contentText1.setTextColor(Color.parseColor(getResources().getString(getResources().getIdentifier(text1Color,"color",context.getPackageName()))));
                contentText1.setGravity(EnumUtils.getGravity(text1Gravity));
                contentText1.setFont(text1Font);

                final CustomFontTextView contentText2 = (CustomFontTextView) content.findViewById(R.id.text_2);
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

                final CustomFontTextView contentText1 = (CustomFontTextView) content.findViewById(R.id.text_1);
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

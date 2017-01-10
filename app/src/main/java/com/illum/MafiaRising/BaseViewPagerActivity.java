package com.illum.MafiaRising;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.regex.Pattern;

public class BaseViewPagerActivity extends FragmentActivity {
    PagerAdapter adapter;
    BidirectionalViewPager pager;
    int slideLayoutID = 0;
    int pagerViewID = 0;
    int pageViewID = 0;
    int titlesResID = 0;
    int swipeDirResID = 0;
    int contentResID = 0;
    int swipeDirDefaultResID = 0;

    //call in onCreate to make fullscreen and give transition animation
    public void init() {
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
    public class PagerAdapter extends FragmentStatePagerAdapter {

        int titlesLen;
        int contentLen;

        PagerAdapter(FragmentManager fm) {
            super(fm);
            titlesLen = getResources().getStringArray(titlesResID).length;
            contentLen = getResources().getStringArray(contentResID).length;
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new PagerSlide();
            Bundle args = new Bundle();
            args.putInt(PagerSlide.SLIDE_NUM, i);
            args.putInt(PagerSlide.SLIDE_LAYOUT, slideLayoutID);
            args.putInt(PagerSlide.PAGE_ID, pageViewID);
            args.putInt(PagerSlide.TITLES_RES, titlesResID);
            args.putInt(PagerSlide.SWIPEDIR_RES, swipeDirResID);
            args.putInt(PagerSlide.CONTENT_RES, contentResID);
            args.putInt(PagerSlide.SWIPEDIR_DEFAULT_RES, swipeDirDefaultResID);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            int len = titlesLen;
            int[] lengths = {titlesLen, contentLen};
            for (int i: lengths) {
                if(i < len) len = i;
            }
            return len;
        }
    }

    public static class PagerSlide extends Fragment {
        public static final String SLIDE_NUM = "SLIDE_NUM";
        public static final String SLIDE_LAYOUT = "SLIDE_LAYOUT";
        public static final String PAGE_ID = "PAGE_ID";
        public static final String TITLES_RES = "TITLES_RES";
        public static final String SWIPEDIR_RES = "SWIPEDIR_RES";
        public static final String CONTENT_RES = "CONTENT_RES";
        public static final String SWIPEDIR_DEFAULT_RES = "SWIPEDIR_DEFAULT_RES";
        String defaultPageSwipeDirection;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Bundle args = getArguments();
            int curSlide = args.getInt(SLIDE_NUM);
            int slideLayoutID = args.getInt(SLIDE_LAYOUT);
            int pageViewID = args.getInt(PAGE_ID);
            int titlesResID = args.getInt(TITLES_RES);
            int swipeDirResID = args.getInt(SWIPEDIR_RES);
            int contentResID = args.getInt(CONTENT_RES);
            int swipeDirDefaultResID = args.getInt(SWIPEDIR_DEFAULT_RES);

            //get slide info
            String[] titlesArray = getResources().getStringArray(titlesResID);
            String[] swipeDirArray = getResources().getStringArray(swipeDirResID);
            String[] contentArray = getResources().getStringArray(contentResID);

            if(swipeDirArray.length == 0) {
                defaultPageSwipeDirection = getResources().getString(swipeDirDefaultResID);
            }

            View rootView = inflater.inflate(slideLayoutID, container, false);
            Context context = rootView.getContext();

            BidirectionalViewPage slide = (BidirectionalViewPage) rootView.findViewById(pageViewID);
            if(defaultPageSwipeDirection != null) {
                slide.setPageSwipeDirection(defaultPageSwipeDirection);
            }
            else {
                slide.setPageSwipeDirection(swipeDirArray[curSlide]);
            }

            //fill page contents
            String headerLayoutColor = "#000000";
            String title = "", titleSize = "0", titleColor = "#000000", titleFont = getResources().getString(R.string.font_name_manteka), titleGravity = "center_vertical";
            String[] titleAttribsUnparsed = titlesArray[curSlide].split(Pattern.quote("`|"));
            for (String titleAttribUnparsed: titleAttribsUnparsed) {
                String[] titleAttrib = titleAttribUnparsed.split(Pattern.quote(":"),2);
                switch (titleAttrib[0]) {
                    case "bkgdColor":
                        headerLayoutColor = titleAttrib[1];
                        break;
                    case "textSize":
                        titleSize = titleAttrib[1];
                        break;
                    case "textColor":
                        titleColor = titleAttrib[1];
                        break;
                    case "textFont":
                        titleFont = titleAttrib[1];
                        break;
                    case "textGravity":
                        titleGravity = titleAttrib[1];
                        break;
                    case "text":
                        title = titleAttrib[1];
                        break;
                }
            }

            View header = slide.findViewById(R.id.header);
            header.setBackgroundColor(Color.parseColor(getResources().getString(getResources().getIdentifier(headerLayoutColor,"color",context.getPackageName()))));

            CustomFontTextView slideTitle = (CustomFontTextView) header.findViewById(R.id.title);
            slideTitle.setText(title);
            slideTitle.setTextSize(Integer.parseInt(titleSize));
            slideTitle.setTextColor(Color.parseColor(getResources().getString(getResources().getIdentifier(titleColor,"color",context.getPackageName()))));
            slideTitle.setGravity(EnumUtils.getGravity(titleGravity));
            slideTitle.setFont(titleFont);

            int contentLayout = 1;
            String contentLayoutColor = "#FFFFFF";
            String text1 = "", text1Size = "0", text1Color = "#FFFFFF", text1Font = getResources().getString(R.string.font_name_manteka), text1Gravity = "center_vertical";
            String text2 = "", text2Size = "0", text2Color = "#FFFFFF", text2Font = getResources().getString(R.string.font_name_manteka), text2Gravity = "center_vertical";
            String img1 = "placeholderbox", img1MaxWidth = "", img1MinWidth = "", img1MaxHeight = "", img1MinHeight = "", img1ScaleType = "";
            String[] contentAttribsUnparsed = contentArray[curSlide].split(Pattern.quote("`|"));
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

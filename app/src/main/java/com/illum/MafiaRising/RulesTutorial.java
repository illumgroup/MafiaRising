package com.illum.MafiaRising;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RulesTutorial extends FragmentActivity {
    RulesTutorialPagerAdapter adapter;
    ViewPager pager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        adapter = new RulesTutorialPagerAdapter(getSupportFragmentManager());
        pager = (ViewPager) findViewById(R.id.tutorial_pager);
        pager.setAdapter(adapter);
    }

    public class RulesTutorialPagerAdapter extends FragmentStatePagerAdapter {
        public RulesTutorialPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new ObjectFragment();
            Bundle args = new Bundle();
            args.putInt(ObjectFragment.ARG_OBJECT, i+1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 100;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Object " + (position + 1);
        }
    }

    public static class ObjectFragment extends Fragment {
        public static final String ARG_OBJECT = "object";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tutorial_slide, container, false);
            Bundle args = getArguments();
            LinearLayout slide = (LinearLayout) rootView.findViewById(R.id.testobj);
            RelativeLayout slideHeader = (RelativeLayout) slide.findViewById(R.id.header);
            TextView slideTitle = (TextView) slideHeader.findViewById(R.id.title);
            slideTitle.setText(Integer.toString(args.getInt(ARG_OBJECT)));
            return rootView;
        }
    }
}

/*public class RulesTutorial extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    }
}*/

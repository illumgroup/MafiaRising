package com.simplex.simplelight.deceived;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class Night extends Fragment {
    private static int night;

    private NightInteractionListener mListener;

    public Night() {
        // Required empty public constructor
    }

    public static Night newInstance(int daynum) {
        Night fragment = new Night();
        night=daynum;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_night, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState){

        super.onViewCreated( view, savedInstanceState );
        final RelativeLayout preview = (RelativeLayout) getView().findViewById(R.id.night);
        preview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                preview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                TextView one=(TextView) getView().findViewById(R.id.nightnumber);
                one.setText(night);
            }
        });
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.nextNight();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NightInteractionListener) {
            mListener = (NightInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement NightInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface NightInteractionListener {
        void nextNight();
    }
}

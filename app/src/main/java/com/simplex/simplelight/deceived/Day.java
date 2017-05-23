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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Day.DayInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Day#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Day extends Fragment {

    private static int day;

    private DayInteractionListener mListener;

    public Day() {
        // Required empty public constructor
    }

    public static Day newInstance(int daynum) {
        Day fragment = new Day();
        day=daynum;
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
        return inflater.inflate(R.layout.fragment_day, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState){

        super.onViewCreated( view, savedInstanceState );
        final RelativeLayout preview = (RelativeLayout) getView().findViewById(R.id.day);
        preview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                preview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                TextView one=(TextView) getView().findViewById(R.id.daynumber);
                one.setText(day);
            }
        });
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.nextDay();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DayInteractionListener) {
            mListener = (DayInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DayInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface DayInteractionListener {
        void nextDay();
    }
}

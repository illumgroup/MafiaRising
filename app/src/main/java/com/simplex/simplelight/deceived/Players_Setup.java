package com.simplex.simplelight.deceived;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

public class Players_Setup extends AppCompatActivity {

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players__setup);
        //overridePendingTransition(R.transition.fadein, R.transition.fadeout); //custom animation

        sharedpreferences = getSharedPreferences("one", Context.MODE_PRIVATE);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION //onscreen nav buttons hide
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN //fullscreen enable
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        NumberPicker np = (NumberPicker) findViewById(R.id.numberPicker);

        np.setMinValue(4);
        np.setMaxValue(25);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("Players", 4);
        editor.commit();

        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt("Players", newVal);
                editor.commit();
            }
        });
    }

    public void playerAssign(View view)
    {
        startActivity(new Intent(Players_Setup.this, CameraCards.class));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus); //Set to fullscreen on focus
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION //onscreen nav buttons hide
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN //fullscreen enable
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }
}

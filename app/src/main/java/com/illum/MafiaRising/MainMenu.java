package com.illum.MafiaRising;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {

    private static int backButtonCount = 0;
    Toast toastExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

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


    public void startGame(View view)
    {
        backButtonCount = 0;
        //startActivity(new Intent(MainMenu.this, Players_Setup.class));
    }

    public void continueGame(View view)
    {
        backButtonCount = 0;

    }

    public void activityOptions(View view)
    {
        backButtonCount = 0;
        /*
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("Test", "Testing Shared Preferences");
        editor.commit();
        */
        Intent intent = new Intent(this, Options.class);
        startActivity(intent);
    }

    public void activityRules(View view)
    {
        backButtonCount = 0;
        Intent intent = new Intent(this, Rules.class);
        startActivity(intent);
    }

    /*
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView(); //set to fullscreen on focus
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

    }

    /*
    @Override
    protected void onRestart() {
        super.onRestart();
        menumusic.startMusic(this, R.raw.firefly);
        menumusic=new musicplayer(this,R.raw.firefly); //restart activity - restart music
    }

    @Override
    protected void onPause() {
        super.onPause();
        menumusic.stopMusic(); //pause activity - stop music
    }

    @Override
    protected void onStop() {
        super.onStop();
        menumusic.endMusicPlayer();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        menumusic.endMusicPlayer(); //release music player and set to null

    }
    */

    @Override
    public void onBackPressed() { //override back button
        if(backButtonCount >= 1)
        {
            if (toastExit != null) toastExit.cancel();
            super.onBackPressed();
        }
        else
        {
            toastExit = Toast.makeText(this, R.string.mm_back_toast, Toast.LENGTH_SHORT);
            toastExit.show();
            backButtonCount++;
        }
    }

}

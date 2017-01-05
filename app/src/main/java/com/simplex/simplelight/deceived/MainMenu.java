package com.simplex.simplelight.deceived;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.IOException;

public class MainMenu extends AppCompatActivity {

    musicplayer menumusic;
    SharedPreferences sharedpreferences;
    private static int backButtonCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
       // overridePendingTransition(R.transition.fadein, R.transition.fadeout); //custom animation

 //       menumusic=new musicplayer(this,R.raw.firefly); //start menu music (temp placeholder)

        sharedpreferences = getSharedPreferences("one", Context.MODE_PRIVATE);
        View decorView = getWindow().getDecorView(); //set to fullscreen
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }

    public void settingsPage(View view)
    {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("Test", "Testing Shared Preferences");
        editor.commit();
        startActivity(new Intent(MainMenu.this, Settings.class)); //open settings menu - settings button onClick
    }

    public void startGame(View view)
    {
        startActivity(new Intent(MainMenu.this, Players_Setup.class)); //open settings menu - settings button onClick
    }

    public void playdescrip(View view)
    {
        startActivity(new Intent(MainMenu.this, HowToPlay.class)); //open How to Play Instructions - howtoplay button onClick
    }


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
 //       menumusic=new musicplayer(this,R.raw.firefly); //restart activity - restart music
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
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }
}

package com.simplex.simplelight.deceived;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    musicplayer settingmusic;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //overridePendingTransition(R.transition.fadein, R.transition.fadeout); //custom animation for activity

 //       settingmusic=new musicplayer(this,R.raw.everybody); //new media player

        View decorView = getWindow().getDecorView(); //set to fullscreen
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        sharedpreferences = getSharedPreferences("one", Context.MODE_PRIVATE);
        String test = sharedpreferences.getString("Test","Error");

        TextView temp=(TextView) findViewById(R.id.sharedtemp);
        temp.setText(test);
    }


    public void back_menu(View view)
    {
        startActivity(new Intent(Settings.this, MainMenu.class)); //on back button press - restart menu activity
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus); //set to fullscreen
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    /*
    @Override
    protected void onRestart() {
        super.onRestart();
        settingmusic.startMusic();
        //       menumusic=new musicplayer(this,R.raw.firefly); //restart activity - restart music
    }

    @Override
    protected void onPause() {
        super.onPause();
        settingmusic.stopMusic(); //on pause activity - stop music
    }

    @Override
    protected void onStop() {
        super.onStop();

        settingmusic.stopMusic(); //on stop activity - stop music
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        settingmusic.endMusicPlayer(); //release mediaplayer resources on activity destroy
    }
    */

}

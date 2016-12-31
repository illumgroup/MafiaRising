package com.illum.MafiaRising;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainMenu extends BaseActivity {

    private static int backButtonCount = 0;
    Toast toastExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        init();

        ViewGroup container = (ViewGroup) findViewById(R.id.mm_root_container);
        for(int i=0;i<container.getChildCount();++i) {
            View v = container.getChildAt(i);
            Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
            fadeIn.setDuration(1000);
            fadeIn.setStartOffset(200+100*i);
            v.startAnimation(fadeIn);
        }

    }

    public void startGame(View view)
    {
        backButtonCount = 0;
        Intent intent = new Intent(this, test_play.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        startActivity(intent);
        //Intent intent = new Intent(this, Players_Setup.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        //startActivity(intent);
        /* Notes 1
        Starting a new game should check to see if there is a game in progress,
        if so, confirm the user wants to start a new game.
        */
        /* NOTES 2
        FLAG_ACTIVITY_TASK_ON_HOME starts the activity and clears activities
        on top of home activity.
        New game screens should use this flag so pressing back will go back
        to the main menu.
        Other screens (eg. options) should be normal.
        */
        /* NOTES 3
        Or use: FLAG_ACTIVITY_NO_HISTORY makes the screen be not kept in history stack.
        So during play, any button that opens a new activity (eg. options)
        should add the current screen to the backstack.
        This way, pressing back from the new activity (eg. options)
        will bring the current screen back.
        We want no history so you can't go back to a previous game screen;
        pressing back will bring you to the main menu.
        */
    }

    public void continueGame(View view)
    {
        backButtonCount = 0;
        /* Notes 3
        Should continueGame be auto-hidden when there is no previous game
        or should it be kept visible and just start a new game?
        auto-hide:
          pros: more whats-the-word/logical?
          cons: there is a change in interface
                will require restructuring of main menu layout
        keep-visible:
          pros: menu can be kept as-is
                need to add more code to main menu class
          cons: less whats-the-word/logical?
        */
    }

    public void activityOptions(View view)
    {
        backButtonCount = 0;
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

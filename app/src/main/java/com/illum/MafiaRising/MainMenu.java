package com.illum.MafiaRising;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.master.permissionhelper.PermissionHelper;

import java.util.Arrays;
import java.util.Set;

//shown after splash screen, launcher-type activity
public class MainMenu extends BaseActivity {

    //keeps track of times back button was hit, should be cleared on any menu button tap
    private static int backButtonCount = 0;
    private boolean permissionsGranted;
    private PermissionHelper permissionHelper;
    //holds toast so it can be dismissed
    Toast toastExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        permissionsGranted = false;
        verifyStoragePermissions();

        init();

        //give every view a fadein animation with delay offset based on child position
        ViewGroup container = (ViewGroup) findViewById(R.id.mm_root_container);
        for(int i=0;i<container.getChildCount();++i) {
            View v = container.getChildAt(i);
            Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
            fadeIn.setDuration(1000);
            fadeIn.setStartOffset(200+100*i);
            v.startAnimation(fadeIn);
        }

    }

    //starts the game, unfinished
    public void startGame(View view)
    {
<<<<<<< HEAD
=======
        backButtonCount = 0;
        verifyStoragePermissions(this);
>>>>>>> 5a0372e75adcab075c5b13259cd2549ad625dbf4

        if(permissionsGranted) {
            System.out.println("Test");

            new AlertDialog.Builder(this)
                    .setTitle(R.string.dialog_title)
                    .setMessage(R.string.dialog_message)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), SetupPlayerCount.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .show();

        }
        /* Notes 1
        Starting a new game should check to see if there is a game in progress,
        if so, confirm the user wants to start a new game.
        */
    }

    //continues previous game session, STUB
    public void continueGame(View view)
    {
<<<<<<< HEAD
=======
        backButtonCount = 0;
        verifyStoragePermissions(this);
>>>>>>> 5a0372e75adcab075c5b13259cd2549ad625dbf4

        if(permissionsGranted) {
        }
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

    //starts options activity
    public void activityOptions(View view)
    {
        backButtonCount = 0;
        Intent intent = new Intent(this, Options.class);
        startActivity(intent);
    }

    //starts rules activity
    public void activityRules(View view)
    {
        backButtonCount = 0;
        Intent intent = new Intent(this, Rules.class);
        startActivity(intent);
    }

    /* not needed atm b/c no music player
    test if switching apps when on a child activity calls the onPause of the main menu activity
    if yes, keep them here
    if no, need to put these in BaseActivity (don't forget viewpagers) and need to figure out how
     access music player accross activities
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

    //permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS= {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private void verifyStoragePermissions() {
        // Check if we have read or write permission

    }


    //override the back button to have confirmation toast, dismisses toast if exiting app
    @Override
    public void onBackPressed() {
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

package com.simplex.simplelight.deceived;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class GamePlay extends AppCompatActivity implements GamePlayFragments.OnFragmentInteractionListener,Night.NightInteractionListener,Day.DayInteractionListener{

    private SharedPreferences sharedpreferences;
    private String[] assignments;
    private int playernum, screennum=0;
    private String[] orderofplay={"night","mafia","doctor","police","day","jury"};
    private int count=1, remainder=0, mafianum=0;
    private boolean[] players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        //overridePendingTransition(R.transition.fadein, R.transition.fadeout); //custom animation
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        //something
        assignments = b.getStringArray("RoleA");

        //hide statusbar and navbar
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION //onscreen nav buttons hide
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN //fullscreen enable
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        //get number of players
        sharedpreferences = getSharedPreferences("one", Context.MODE_PRIVATE);
        playernum = sharedpreferences.getInt("Players",0);

        //set number of players active - death tracker
        players=new boolean[playernum];
        for(int a=0;a<playernum;a++)
        {
            players[a]=true;
        }

        //set number of mafia
        mafianum=playernum/4;
        remainder=playernum-mafianum;

        if(savedInstanceState==null){
            //launch night fragment
                launchNight();

        }
        //    launchMafia();

    }

    private void launchNight(){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.game_layout, Night.newInstance(count), "GamePlay")
                .commit();
    }

    private void launchDay(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.game_layout, Day.newInstance(count), "GamePlay")
                .commit();
    }

    private void launchMafia(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.game_layout, GamePlayFragments.newInstance(assignments, "Mafia", players), "GamePlay")
                .commit();
    }

    private void launchDoctor(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.game_layout, GamePlayFragments.newInstance(assignments, "Doctor", players), "GamePlay")
                .commit();
    }

    private void launchPolice(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.game_layout, GamePlayFragments.newInstance(assignments, "Police", players), "GamePlay")
                .commit();
    }

    private void launchJury(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.game_layout, GamePlayFragments.newInstance(assignments, "Jury", players), "GamePlay")
                .commit();
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

    @Override
    public void onResume()
    {
        super.onResume();
        int num = sharedpreferences.getInt("Players",0);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void gamePlayFragment(boolean[] selectedValues){
        if(screennum==1) {
            screennum++;
            launchDoctor();
        }else if(screennum==2){
            screennum++;
            launchPolice();
        }else if(screennum==3){
            screennum++;
            launchJury();
        }else {
            for(int a=0;a<selectedValues.length;a++)
            {
                if(selectedValues[a]==true)
                    players[a]=false;
            }
            launchDay();
        }
    }

    @Override
    public void nextNight() {
        screennum++;
        launchMafia();
    }

    @Override
    public void nextDay() {
        screennum=1;
        remainder=playernum-mafianum;
        if(remainder>mafianum)
            launchNight();
    }
}

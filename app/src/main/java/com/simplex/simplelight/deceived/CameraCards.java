package com.simplex.simplelight.deceived;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CameraCards extends AppCompatActivity implements AssignPlayer.OnNextPlayer{

    private String[] roles;
    private SharedPreferences sharedpreferences;
    private int ratio,num, playernum=0;
    private int[] mafiaplaces;
    private String[] assignments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_cards);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION //onscreen nav buttons hide
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN //fullscreen enable
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        final Resources res = this.getResources();
        roles = res.getStringArray(R.array.roles);

        sharedpreferences = getSharedPreferences("one", Context.MODE_PRIVATE);
        num = sharedpreferences.getInt("Players",0);
        assignments=new String[num];

        ratio=num/4;
        mafiaplaces=new int[ratio];
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=1; i<num; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        for (int i=0; i<ratio; i++) {
            mafiaplaces[i]=list.get(i);
        }
        for(int i=0;i<num;i++)
        {
            assignments[i]="Citizen";
            for(int mafiasel=0;mafiasel<ratio;mafiasel++)
            {
                if(mafiaplaces[mafiasel]==i)
                    assignments[i]="Mafia";
            }
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.root_layout, AssignPlayer.newInstance(playernum), "CameraCards")
                    .commit();
        }
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
    public void onFragmentInteraction(){

        playernum++;
        if(playernum==assignments.length) {
            Intent intent = new Intent(this, GamePlay.class);
            Bundle b = new Bundle();
            b.putStringArray("RoleA", assignments);
            intent.putExtras(b);
            startActivity(intent);

        }

        getSupportFragmentManager().beginTransaction().replace(R.id.root_layout, AssignPlayer.newInstance(playernum), "CameraCards").commit();
    }
}

package com.illum.MafiaRising;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

//shows splash screen, starts main menu activity, then finishes itself
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        //finish itself so user cannot navigate back to it
        finish();
    }
}

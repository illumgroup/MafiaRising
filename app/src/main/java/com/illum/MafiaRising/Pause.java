package com.illum.MafiaRising;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

//shows pause menu screen
public class Pause extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause);

        init();
    }

    //backMainMenu is in BaseActivity
    //called when Home button is pressed

    //starts pause roles activity, shows current players and roles
    public void activityRoles(View view) {
        Intent intent = new Intent(this, PauseRoles.class);
        startActivity(intent);
    }

    //starts options activity
    public void activityOptions(View view) {
        Intent intent = new Intent(this, Options.class);
        startActivity(intent);
    }

    //starts rules activity
    public void activityRules(View view) {
        Intent intent = new Intent(this, Rules.class);
        startActivity(intent);
    }

}

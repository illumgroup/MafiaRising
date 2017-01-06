package com.illum.MafiaRising;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//shows rules menu screen, parents include main menu and pause screens
public class Rules extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        init();
    }

    //starts tutorial viewPager fragment activity
    public void activityTutorial(View view) {
        Intent intent = new Intent(this, RulesTutorial.class);
        startActivity(intent);
    }

    //starts script viewPager fragment activity
    public void activityScript(View view) {
        Intent intent = new Intent(this, RulesScript.class);
        startActivity(intent);
    }

    //starts roles activity
    public void activityRoles(View view) {
        Intent intent = new Intent(this, RulesRoles.class);
        startActivity(intent);
    }

}

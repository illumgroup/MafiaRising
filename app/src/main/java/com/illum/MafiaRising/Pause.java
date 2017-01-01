package com.illum.MafiaRising;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class Pause extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause);

        init();
    }

    public void backMainMenu(View view) {
        clearPrevious();
    }

    public void activityRoles(View view) {
        Intent intent = new Intent(this, PauseRoles.class);
        startActivity(intent);
    }

    public void activityOptions(View view) {
        Intent intent = new Intent(this, Options.class);
        startActivity(intent);
    }

    public void activityRules(View view) {
        Intent intent = new Intent(this, Rules.class);
        startActivity(intent);
    }

}

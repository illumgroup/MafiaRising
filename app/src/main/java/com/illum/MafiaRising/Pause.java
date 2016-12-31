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

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }

    public void backMainMenu(View view) {
        Intent intent = new Intent();
        intent.putExtra("exitCode",1);
        setResult(Activity.RESULT_OK,intent);
        finish();
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

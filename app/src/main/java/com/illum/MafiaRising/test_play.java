package com.illum.MafiaRising;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class test_play extends BaseActivity {

    static final int PAUSE_SCREEN_REQUEST = 13975;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        init();
    }

    public void activityPause(View view) {
        Intent intent = new Intent(this, Pause.class);
        startActivityForResult(intent, PAUSE_SCREEN_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PAUSE_SCREEN_REQUEST) {
            if(resultCode == Activity.RESULT_OK) {
                if (data.getIntExtra("exitCode", 0) == 1) {
                    super.onBackPressed();
                }
            }
        }
    }

}

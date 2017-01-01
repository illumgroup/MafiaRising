package com.illum.MafiaRising;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SetupPlayerCards extends BaseActivity {

    SharedPreferences sharedPrefs;
    private int playerIndex;
    private int playerCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_player_cards);

        init();

        final String sharedPrefsFileKey = getString(R.string.app_package) + "." + getString(R.string.game_session_file_key);
        sharedPrefs = getSharedPreferences(sharedPrefsFileKey, Context.MODE_PRIVATE);

        final String sharedPrefsPlayerCountkey = getString(R.string.app_package) + "." + getString(R.string.game_session_player_count_key);
        final String sharedPrefsPlayerCountMin = getString(R.string.game_session_player_count_min);
        playerCount = sharedPrefs.getInt(sharedPrefsPlayerCountkey, Integer.parseInt(sharedPrefsPlayerCountMin));

        playerIndex = getIntent().getIntExtra("playerIndex", playerCount);

        //TODO: learn how to use camera to take pictures
        //TODO: store images somewhere secure
        //TODO: delete previous images if still present (in case game crashed last time)
        //TODO: on btn press take picture
        //TODO: after take image, replace placeholder with image or set image as src and placeholder as bkgd and set right arrow to visible and set camera btn to hide
    }

    public void onClickNext(View view) {
        if(playerIndex >= playerCount-1) {
            Intent intent = new Intent(this, GameReady.class);
            startActivityForResult(intent, BaseActivity.REQUEST_EXIT_CODE);

            clearPrevious();
        }
        else {
            Intent intent = new Intent(this, SetupPlayerCards.class);
            intent.putExtra("playerIndex", playerIndex + 1);
            startActivityForResult(intent, BaseActivity.REQUEST_EXIT_CODE);
        }
    }
}

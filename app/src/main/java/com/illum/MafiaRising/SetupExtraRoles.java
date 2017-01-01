package com.illum.MafiaRising;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class SetupExtraRoles extends BaseActivity {

    SharedPreferences sharedPrefs;
    int playerCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_extra_roles);

        init();

        final String sharedPrefsFileKey = getString(R.string.app_package) + "." + getString(R.string.game_session_file_key);
        sharedPrefs = getSharedPreferences(sharedPrefsFileKey, Context.MODE_PRIVATE);

        final String sharedPrefsPlayerCountkey = getString(R.string.app_package) + "." + getString(R.string.game_session_player_count_key);
        final String sharedPrefsPlayerCountMin = getString(R.string.game_session_player_count_min);
        playerCount = sharedPrefs.getInt(sharedPrefsPlayerCountkey, Integer.parseInt(sharedPrefsPlayerCountMin));

        //TODO: dynamically generate extra roles checkbox list from string array
        //TODO: give checkbox listeners to each list item
        //TODO: save checkbox choices to sharedpref
    }

    public void onClickNext(View view) {
        Intent intent = new Intent(this, SetupPlayerCards.class);
        intent.putExtra("playerIndex",0);
        startActivityForResult(intent, BaseActivity.REQUEST_EXIT_CODE);

        clearPrevious();
    }

}

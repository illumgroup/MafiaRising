package com.illum.MafiaRising;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SetupPlayerCount extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_player_count);

        init();
    }

    public void onClickNext(View view) {
        Intent intent = new Intent(this, SetupExtraRoles.class);
        //TODO: get numberpicker working and replace hard-coded int
        //TODO: save numPlayers to game session pref
        startActivityForResult(intent, BaseActivity.REQUEST_EXIT_CODE);
    }

}

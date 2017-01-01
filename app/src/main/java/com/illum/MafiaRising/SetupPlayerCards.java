package com.illum.MafiaRising;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SetupPlayerCards extends BaseActivity {

    private int playerIndex;
    private int playerCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_player_cards);

        playerIndex = getIntent().getIntExtra("playerIndex",1);
        //TODO: grab number of players from game session pref

        init();
    }

    public void onClickNext(View view) {
        if(playerIndex <= 1) {
            Intent intent = new Intent(this, GameReady.class);
            startActivityForResult(intent, BaseActivity.REQUEST_EXIT_CODE);

            clearPrevious();
        }
        else {
            Intent intent = new Intent(this, SetupPlayerCards.class);
            intent.putExtra("playerIndex", playerIndex - 1);
            startActivityForResult(intent, BaseActivity.REQUEST_EXIT_CODE);
        }
    }
}

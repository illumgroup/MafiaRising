package com.illum.MafiaRising;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SetupPlayers extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_players);

        init();
    }

    public void onClickNext(View view) {
        Intent intent = new Intent(this, test_play.class);
        startActivity(intent);
        finish();
    }

}

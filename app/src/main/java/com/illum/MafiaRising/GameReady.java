package com.illum.MafiaRising;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//shown after setup and before game started
public class GameReady extends BaseActivity {

    private String[] assignments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_ready);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        assignments = b.getStringArray("RoleA");
        init();
    }

    //backMainMenu is in BaseActivity
    //called when Home button is pressed

    //called when user hits next button
    //starts game, sets checkpoint (finishes previous screens up to main menu)
    public void onClickNext(View view) {
        Intent intent = new Intent(this, GamePlay.class);
        Bundle b = new Bundle();
        b.putStringArray("RoleA", assignments);
        intent.putExtras(b);
        startActivityForResult(intent, BaseActivity.REQUEST_EXIT_CODE);

        clearPrevious();
    }

}

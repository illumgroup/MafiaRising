package com.illum.MafiaRising;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GameReady extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_ready);

        init();
    }

    public void backMainMenu(View view) {
        Intent intent = new Intent();
        intent.putExtra("exitCode",BaseActivity.CODE_RETURN_TO_MAIN);
        setResult(Activity.RESULT_OK,intent);
        super.onBackPressed();
    }

    public void onClickNext(View view) {
        Intent intent = new Intent(this, GamePlay.class);
        startActivityForResult(intent, BaseActivity.REQUEST_EXIT_CODE);

        clearPrevious();
    }

}

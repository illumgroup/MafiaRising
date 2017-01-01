package com.illum.MafiaRising;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SetupExtraRoles extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_extra_roles);

        init();
    }

    public void onClickNext(View view) {
        Intent intent = new Intent(this, SetupPlayerCards.class);
        //TODO: grab number of players from game session pref
        intent.putExtra("playerIndex",5);
        startActivityForResult(intent, BaseActivity.REQUEST_EXIT_CODE);

        clearPrevious();
    }

}

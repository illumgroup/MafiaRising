package com.illum.MafiaRising;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

public class SetupPlayerCount extends BaseActivity {

    SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_player_count);

        init();

        final String sharedPrefsFileKey = getString(R.string.app_package) + "." + getString(R.string.game_session_file_key);
        sharedPrefs = getSharedPreferences(sharedPrefsFileKey, Context.MODE_PRIVATE);

        final String sharedPrefsPlayerCountkey = getString(R.string.app_package) + "." + getString(R.string.game_session_player_count_key);
        final String sharedPrefsPlayerCountMin = getString(R.string.game_session_player_count_min);
        final String sharedPrefsPlayerCountMax = getString(R.string.game_session_player_count_max);

        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numPlayersPicker);
        numberPicker.setMinValue(Integer.parseInt(sharedPrefsPlayerCountMin));
        numberPicker.setMaxValue(Integer.parseInt(sharedPrefsPlayerCountMax));

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                SharedPreferences.Editor sharedPrefsEditor = sharedPrefs.edit();
                sharedPrefsEditor.putInt(sharedPrefsPlayerCountkey, newVal);
                sharedPrefsEditor.apply();
            }
        });

    }

    public void onClickNext(View view) {
        Intent intent = new Intent(this, SetupExtraRoles.class);
        startActivityForResult(intent, BaseActivity.REQUEST_EXIT_CODE);
    }

}

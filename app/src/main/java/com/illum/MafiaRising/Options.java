package com.illum.MafiaRising;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;

//shows options screen
public class Options extends BaseActivity {

    //holds sharedPrefs specific to options screen
    SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        init();

        //get options sharedPref file key and open in private mode so only this can access it
        final String sharedPrefsFileKey = getString(R.string.app_package) + "." + getString(R.string.options_pref_file_key);
        sharedPrefs = getSharedPreferences(sharedPrefsFileKey, Context.MODE_PRIVATE);

        //get options sharedPref keys and defaults
        final String sharedPrefsAiDirectionsKey = getString(R.string.app_package) + "." +  getString(R.string.options_pref_ai_directions);
        final String sharedPrefsAiDirectionsDefault = getString(R.string.options_pref_ai_directions_default);
        final String sharedPrefsAiStoryKey = getString(R.string.app_package) + "." +  getString(R.string.options_pref_ai_story);
        final String sharedPrefsAiStoryDefault = getString(R.string.options_pref_ai_story_default);
        final String sharedPrefsVolSfxKey = getString(R.string.app_package) + "." +  getString(R.string.options_pref_vol_sfx);
        final String sharedPrefsVolSfxDefault = getString(R.string.options_pref_vol_sfx_default);
        final String sharedPrefsVolBkgdKey = getString(R.string.app_package) + "." +  getString(R.string.options_pref_vol_bkgd);
        final String sharedPrefsVolBkgdDefault = getString(R.string.options_pref_vol_bkgd_default);

        //get directions checkbox, set default state and listener that sets sharedPref
        final CheckBox aiDirections = (CheckBox) findViewById(R.id.directions_checkbox);
        aiDirections.setChecked(sharedPrefs.getBoolean(sharedPrefsAiDirectionsKey,Boolean.parseBoolean(sharedPrefsAiDirectionsDefault)));
        aiDirections.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton box, boolean isChecked) {
                SharedPreferences.Editor sharedPrefsEditor = sharedPrefs.edit();
                sharedPrefsEditor.putBoolean(sharedPrefsAiDirectionsKey,isChecked);
                sharedPrefsEditor.apply();
            }
        });

        //get story checkbox, set default state and listener that sets sharedPref
        final CheckBox aiStory = (CheckBox) findViewById(R.id.story_checkbox);
        aiStory.setChecked(sharedPrefs.getBoolean(sharedPrefsAiStoryKey,Boolean.parseBoolean(sharedPrefsAiStoryDefault)));
        aiStory.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton box, boolean isChecked) {
                SharedPreferences.Editor sharedPrefsEditor = sharedPrefs.edit();
                sharedPrefsEditor.putBoolean(sharedPrefsAiStoryKey,isChecked);
                sharedPrefsEditor.apply();
            }
        });

        //get sfx seekbar, set default state and listener that sets sharedPref
        final SeekBar volumeSfx = (SeekBar) findViewById(R.id.sfx_bar);
        volumeSfx.setProgress(sharedPrefs.getInt(sharedPrefsVolSfxKey, Integer.parseInt(sharedPrefsVolSfxDefault)));
        volumeSfx.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressVolumeSfx = Integer.parseInt(sharedPrefsVolSfxDefault);
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressVolumeSfx = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                SharedPreferences.Editor sharedPrefsEditor = sharedPrefs.edit();
                sharedPrefsEditor.putInt(sharedPrefsVolSfxKey,progressVolumeSfx);
                sharedPrefsEditor.apply();
            }
        });

        //get bkgd music seekbar, set default state and listener that sets sharedPref
        final SeekBar volumeBkgd = (SeekBar) findViewById(R.id.music_bar);
        volumeBkgd.setProgress(sharedPrefs.getInt(sharedPrefsVolBkgdKey, Integer.parseInt(sharedPrefsVolBkgdDefault)));
        volumeBkgd.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressVolumeBkgd = Integer.parseInt(sharedPrefsVolBkgdDefault);

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressVolumeBkgd = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                SharedPreferences.Editor sharedPrefsEditor = sharedPrefs.edit();
                sharedPrefsEditor.putInt(sharedPrefsVolBkgdKey,progressVolumeBkgd);
                sharedPrefsEditor.apply();
            }
        });
    }

    //start options info activity on info button tap
    public void activityOptionsInfo(View view) {
        Intent intent = new Intent(this, OptionsInfo.class);
        startActivity(intent);
    }
}

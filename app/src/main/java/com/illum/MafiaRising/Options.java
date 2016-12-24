package com.illum.MafiaRising;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

public class Options extends BaseActivity {

    private SeekBar volumeSfx;
    private SeekBar volumeBkgd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        volumeSfx = (SeekBar) findViewById(R.id.sfx_bar);
        volumeSfx.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO store sfx seekbar value to options variable
                Toast.makeText(Options.this,"sfx volume:"+progressChanged, Toast.LENGTH_SHORT).show();
            }
        });

        volumeBkgd = (SeekBar) findViewById(R.id.music_bar);
        volumeBkgd.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO store bkgd seekbar value to options variable
                Toast.makeText(Options.this,"bkgd music volume:"+progressChanged, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void activityOptionsInfo(View view) {
        Intent intent = new Intent(this, OptionsInfo.class);
        startActivity(intent);
    }
}

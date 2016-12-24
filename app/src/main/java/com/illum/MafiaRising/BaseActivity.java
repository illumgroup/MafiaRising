package com.illum.MafiaRising;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Chelky on 12/23/2016.
 */

public class BaseActivity extends AppCompatActivity {

    public void back_menu(View view) {
        super.onBackPressed();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
        }
    }
}

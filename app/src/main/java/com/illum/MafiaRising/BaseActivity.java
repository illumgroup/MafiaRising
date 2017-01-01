package com.illum.MafiaRising;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class BaseActivity extends AppCompatActivity {

    public static final int REQUEST_EXIT_CODE = 13975;
    public static final int CODE_DEFAULT = 0;
    public static final int CODE_RETURN_TO_MAIN = 1;
    public static final int CODE_CLEAR_PREVIOUS = 2;

    public void init() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void onClickBack(View view) {
        super.onBackPressed();

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void onClickPause(View view) {
        Intent intent = new Intent(this, Pause.class);
        startActivityForResult(intent, REQUEST_EXIT_CODE);
    }

    public void clearPrevious() {
        Intent back_intent = new Intent();
        back_intent.putExtra("exitCode",BaseActivity.CODE_CLEAR_PREVIOUS);
        setResult(Activity.RESULT_OK,back_intent);
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_EXIT_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                int exitCode = data.getIntExtra("exitCode", 0);
                if (exitCode == CODE_RETURN_TO_MAIN) {
                    Intent intent = new Intent();
                    intent.putExtra("exitCode",BaseActivity.CODE_RETURN_TO_MAIN);
                    setResult(Activity.RESULT_OK,intent);
                    super.onBackPressed();
                }
                else if(exitCode == CODE_CLEAR_PREVIOUS) {
                    Intent intent = new Intent();
                    intent.putExtra("exitCode",BaseActivity.CODE_CLEAR_PREVIOUS);
                    setResult(Activity.RESULT_OK,intent);
                    super.onBackPressed();
                }
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }
}

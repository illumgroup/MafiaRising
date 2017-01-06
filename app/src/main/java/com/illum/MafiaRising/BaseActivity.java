package com.illum.MafiaRising;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

//class with convenience functions, nearly all activities should extend this
public class BaseActivity extends AppCompatActivity {

    //random constant number for exit code
    public static final int REQUEST_EXIT_CODE = 13975;
    public static final int CODE_DEFAULT = 0;           //unused, just here if it is ever needed
    public static final int CODE_RETURN_TO_MAIN = 1;
    public static final int CODE_CLEAR_PREVIOUS = 2;

    //call in onCreate to make fullscreen and give transition animation
    public void init() {
        //make fullscreen
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        //activity transition animation
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    //convenient back button handler with transition animation
    public void onClickBack(View view) {
        super.onBackPressed();

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    //game activity convenience function for pause button to bring up Pause activity
    public void onClickPause(View view) {
        Intent intent = new Intent(this, Pause.class);
        //start pause activity asking for a result (exit code)
        startActivityForResult(intent, REQUEST_EXIT_CODE);
    }

    //game activity convenience function to return back to main menu
    public void backMainMenu(View view) {
        Intent intent = new Intent();
        intent.putExtra("exitCode",BaseActivity.CODE_RETURN_TO_MAIN);
        setResult(Activity.RESULT_OK,intent);
        super.onBackPressed();
    }

    //game activity convenience function to start exit chain
    // used when current activity is one where the user should not be able to go back to previous
    // finishes current activity, should it not? 51% should not, 49% should
    public void clearPrevious() {
        Intent back_intent = new Intent();
        back_intent.putExtra("exitCode",BaseActivity.CODE_CLEAR_PREVIOUS);
        setResult(Activity.RESULT_OK,back_intent);
        super.onBackPressed();
    }

    //handles exit codes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_EXIT_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                int exitCode = data.getIntExtra("exitCode", CODE_DEFAULT);
                //CODE_RETURN_TO_MAIN is the same as CODE_CLEAR_PREVIOUS
                // I kept both so they can make logical sense when reading e.g. backMainMenu
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

    //handle focus change events with transition animation, restores fullscreen
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

package com.illum.MafiaRising;

import android.os.Bundle;

//options info screen, shown when user hits info btn on options activity
public class OptionsInfo extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_info);

        init();
    }

}

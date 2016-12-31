package com.illum.MafiaRising;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class PauseRoles extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause_roles);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        ArrayList roles = getRolesList();

        final ListView listView = (ListView) findViewById(R.id.roles_list);
        PauseRolesListAdapter arrayAdapter = new PauseRolesListAdapter(this, roles);
        listView.setAdapter(arrayAdapter);
    }

    private ArrayList getRolesList() {
        ArrayList<PauseRolesItem> results = new ArrayList<PauseRolesItem>();
        results.add(new PauseRolesItem("Citizen",R.drawable.placeholderbox));
        results.add(new PauseRolesItem("Mafia",R.drawable.placeholderbox));
        results.add(new PauseRolesItem("Police",R.drawable.placeholderbox));
        results.add(new PauseRolesItem("Citizen",R.drawable.placeholderbox));
        results.add(new PauseRolesItem("Citizen",R.drawable.placeholderbox));
        results.add(new PauseRolesItem("Mafia",R.drawable.placeholderbox));
        results.add(new PauseRolesItem("Doctor",R.drawable.placeholderbox));
        results.add(new PauseRolesItem("Moderator",R.drawable.placeholderbox));
        /*
        for(int i;i<numPlayers;++i) {
            results.add(new PauseRolesItem(playerRoles[i],playerImgs[i]);
        }
         */
        return results;
    }

}

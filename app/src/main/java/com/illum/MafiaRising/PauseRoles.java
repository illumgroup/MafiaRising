package com.illum.MafiaRising;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class PauseRoles extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause_roles);

        init();

        List roles = getRolesList();

        final ListView listView = (ListView) findViewById(R.id.roles_list);
        PauseRolesListAdapter arrayAdapter = new PauseRolesListAdapter(this, roles);
        listView.setAdapter(arrayAdapter);
    }

    private List getRolesList() {
        List<PauseRolesItem> results = new ArrayList<PauseRolesItem>();
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

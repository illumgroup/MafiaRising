package com.illum.MafiaRising;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

//shows current players and roles, unfinished
//requires PauseRolesItem and PauseRolesListAdapter
public class PauseRoles extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause_roles);

        init();

        //get list of players and roles
        List roles = getRolesList();

        //set ListView using list
        final ListView listView = (ListView) findViewById(R.id.roles_list);
        PauseRolesListAdapter arrayAdapter = new PauseRolesListAdapter(this, roles);
        listView.setAdapter(arrayAdapter);
    }

    //gets the list of players and roles for current game session
    private List getRolesList() {
        List<PauseRolesItem> results = new ArrayList<PauseRolesItem>();
        //dummy list for now
        results.add(new PauseRolesItem("Citizen",R.drawable.placeholderbox));
        results.add(new PauseRolesItem("Mafia",R.drawable.placeholderbox));
        results.add(new PauseRolesItem("Police",R.drawable.placeholderbox));
        results.add(new PauseRolesItem("Citizen",R.drawable.placeholderbox));
        results.add(new PauseRolesItem("Citizen",R.drawable.placeholderbox));
        results.add(new PauseRolesItem("Mafia",R.drawable.placeholderbox));
        results.add(new PauseRolesItem("Doctor",R.drawable.placeholderbox));
        results.add(new PauseRolesItem("Moderator",R.drawable.placeholderbox));
        /* TODO: Pause Roles list should be dynamically generated probably from game shared pref
             maybe keep loaded so it doesn't have to be
             created again (because it may be cpu intensive)
        for(int i;i<numPlayers;++i) {
            results.add(new PauseRolesItem(playerRoles[i],playerImgs[i]);
        }
         */
        return results;
    }

}

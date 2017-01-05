package com.illum.MafiaRising;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetupExtraRoles extends BaseActivity {

    SharedPreferences sharedPrefs;
    int playerCount;
    int rolesExtrasLen;
    List<String> rolesExtrasNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_extra_roles);

        init();

        final String sharedPrefsFileKey = getString(R.string.app_package) + "." + getString(R.string.game_session_file_key);
        sharedPrefs = getSharedPreferences(sharedPrefsFileKey, Context.MODE_PRIVATE);

        final String sharedPrefsPlayerCountkey = getString(R.string.app_package) + "." + getString(R.string.game_session_player_count_key);
        final String sharedPrefsPlayerCountMin = getString(R.string.game_session_player_count_min);
        playerCount = sharedPrefs.getInt(sharedPrefsPlayerCountkey, Integer.parseInt(sharedPrefsPlayerCountMin));

        String[] rolesNames = getResources().getStringArray(R.array.roles_names);
        String[] rolesUseFlags = getResources().getStringArray(R.array.roles_use_flags);
        String roleFlagExtra = getResources().getString(R.string.role_use_flag_extra);

        rolesExtrasNames = new ArrayList<>();
        for(int i=0;i<rolesUseFlags.length;++i) {
            if(rolesUseFlags[i].equals(roleFlagExtra)) {
                rolesExtrasNames.add(rolesNames[i]);
            }
        }

        rolesExtrasLen = rolesExtrasNames.size();

        ViewGroup content = (ViewGroup) findViewById(R.id.content);

        for(int i = 0; i < rolesExtrasLen; ++i) {
            getLayoutInflater().inflate(R.layout.fragment_setup_extra_roles_item, content, true);
            View role_layout = content.getChildAt(i);
            CustomFontTextView role_text = (CustomFontTextView) role_layout.findViewById(R.id.role_text);
            role_text.setText(rolesExtrasNames.get(i));
            CheckBox role_box = (CheckBox) role_layout.findViewById(R.id.role_checkbox);
            role_box.setChecked(false);
        }
    }

    public void onClickNext(View view) {
        List<String> rolesNames = Arrays.asList(getResources().getStringArray(R.array.roles_names));
        String sharedPrefsSetupExtraRolesKey = getString(R.string.app_package) + "." + getString(R.string.game_session_extra_roles_key);

        Set<String> selectedExtraRoles = new HashSet<>();

        ViewGroup content = (ViewGroup) findViewById(R.id.content);
        for(int i = 0; i< rolesExtrasLen; ++i) {
            View role_layout = content.getChildAt(i);
            TextView role_text = (TextView) role_layout.findViewById(R.id.role_text);
            CheckBox role_box = (CheckBox) role_layout.findViewById(R.id.role_checkbox);
            if(role_box.isChecked()) {
                String role_name = (String) role_text.getText();
                int roleIndex = rolesNames.indexOf(role_name);
                selectedExtraRoles.add(Integer.toString(roleIndex));
            }
        }

        SharedPreferences.Editor sharedPrefsEditor = sharedPrefs.edit();
        sharedPrefsEditor.putStringSet(sharedPrefsSetupExtraRolesKey,selectedExtraRoles);
        sharedPrefsEditor.apply();

        Intent intent = new Intent(this, SetupPlayerCards.class);
        intent.putExtra("SetupPlayerIndex",0);
        startActivityForResult(intent, BaseActivity.REQUEST_EXIT_CODE);

        clearPrevious();
    }

}

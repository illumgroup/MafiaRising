package com.illum.MafiaRising;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

//shows rules roles menu screen
// dynamically generated from names string-array in string resources
public class RulesRoles extends BaseActivity {

    String[] rolesNames;
    int rolesLen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_roles);

        init();

        //get available names and array length
        rolesNames = getResources().getStringArray(R.array.roles_names);
        rolesLen = rolesNames.length;
        //calculate distributed weight for each button
        double btn_weight = 1.0/rolesLen;

        ViewGroup content = (ViewGroup) findViewById(R.id.content);
        //create a button for each role name with distributed weight for height
        for(int i=0;i<rolesLen;++i) {
            getLayoutInflater().inflate(R.layout.fragment_rules_roles_button, content, true);
            View btnView = content.getChildAt(i);
            CustomFontButton btn = (CustomFontButton) btnView.findViewById(R.id.btn);
            btn.setText(rolesNames[i]);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) btnView.getLayoutParams();
            params.weight = (float)btn_weight;
            btnView.setLayoutParams(params);
        }

    }

    //generic btn click handler, uses child index to pass to reusable screen
    public void onClickRoleBtn(View btn) {
        //get the layout root
        // btns are in a content->holder->btn hierarchy
        View btnHolder = (View) btn.getParent();
        ViewGroup content = (ViewGroup) btnHolder.getParent();
        int i = content.indexOfChild(btnHolder);
        Intent intent = new Intent(this, RulesRolesRole.class);
        intent.putExtra("RulesRolesIndex",i);
        startActivity(intent);
    }
}

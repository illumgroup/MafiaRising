package com.illum.MafiaRising;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class RulesRoles extends BaseActivity {

    int rolesLen;
    String[] rolesNames, rolesStances, rolesInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_roles);

        init();

        rolesNames = getResources().getStringArray(R.array.roles_names);
        rolesStances = getResources().getStringArray(R.array.roles_stances);
        rolesInfo = getResources().getStringArray(R.array.roles_info);

        rolesLen = rolesNames.length;
        double btn_weight = 1.0/rolesLen;

        ViewGroup content = (ViewGroup) findViewById(R.id.content);

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

    public void onClickRoleBtn(View btn) {
        View btnHolder = (View) btn.getParent();
        ViewGroup content = (ViewGroup) btnHolder.getParent();
        int i = content.indexOfChild(btnHolder);
        Intent intent = new Intent(this, RulesRole.class);
        intent.putExtra("index",i);
        startActivity(intent);
    }
}

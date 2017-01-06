package com.illum.MafiaRising;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

//reusable activity for each role info screen
public class RulesRolesRole extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_rules_role);

        init();

        //get all role info
        //TODO: would making these global static variables improve performance?
        String[] rolesNames = getResources().getStringArray(R.array.roles_names);
        String[] rolesStances = getResources().getStringArray(R.array.roles_stances);
        String[] rolesImgs = getResources().getStringArray(R.array.roles_imgs);
        String[] rolesInfo = getResources().getStringArray(R.array.roles_info);

        //get the role index, default to 0
        int i = getIntent().getIntExtra("RulesRolesIndex",0);

        //set all customizable parts based on role info
        //set header
        View header = findViewById(R.id.header);
        View headerContent = header.findViewById(R.id.header_content);
        //set role name
        CustomFontTextView title = (CustomFontTextView) headerContent.findViewById(R.id.title);
        title.setText(rolesNames[i]);
        //set role stance
        CustomFontTextView titleSub = (CustomFontTextView) headerContent.findViewById(R.id.subtitle);
        titleSub.setText(rolesStances[i]);

        //set content
        View content = findViewById(R.id.content);
        View contentImgHolder = content.findViewById(R.id.content_img);
        //set role img
        ImageView img = (ImageView) contentImgHolder.findViewById(R.id.role_img);
        img.setImageDrawable(ContextCompat.getDrawable(this,getResources().getIdentifier(rolesImgs[i],"drawable",this.getPackageName())));
        //set role text
        View contentTextHolder = content.findViewById(R.id.content_text);
        TextView text = (TextView) contentTextHolder.findViewById(R.id.role_info);
        text.setText(rolesInfo[i]);
    }
}

package com.illum.MafiaRising;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

public class PauseRolesListAdapter extends ArrayAdapter {

    private Context context;

    public PauseRolesListAdapter(Context context, List items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.context = context;
    }

    private class ListItemHolder {
        ImageView roleImage;
        CustomFontTextView roleName;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ListItemHolder holder = null;
        PauseRolesItem RoleItem = (PauseRolesItem) getItem(position);
        View viewToUse = null;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
            viewToUse = inflater.inflate(R.layout.fragment_pause_roles_item, null);

            holder = new ListItemHolder();
            holder.roleImage = (ImageView) viewToUse.findViewById(R.id.role_img);
            holder.roleName = (CustomFontTextView) viewToUse.findViewById(R.id.role_name);
            viewToUse.setTag(holder);
        }
        else {
            viewToUse = convertView;
            holder = (ListItemHolder) viewToUse.getTag();
        }

        holder.roleName.setText(RoleItem.getRoleName());
        holder.roleImage.setImageResource(RoleItem.getImgName());
        return viewToUse;
    }

}

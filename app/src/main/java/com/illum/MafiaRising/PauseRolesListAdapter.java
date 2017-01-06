package com.illum.MafiaRising;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

//this adapter manipulates objects of type PauseRolesItem -> extends ArrayAdapter<PauseRolesItem>
//I'm still not entirely sure how this works (adapted from an online tutorial/example) :P
class PauseRolesListAdapter extends ArrayAdapter<PauseRolesItem> {

    private Context context;

    PauseRolesListAdapter(Context context, List<PauseRolesItem> items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.context = context;
    }

    //simple class to hold views for pause roles item
    //different from PauseRolesItem
    // this holds the actual views whereas PauseRolesItem holds values to be displayed
    private class ViewHolder {
        ImageView roleImage;
        CustomFontTextView roleName;
    }

    //main part of the adapter, returns the adapter view to be displayed
    //called when a new item needs to be shown
    // convertView is a recyclable non-visible view
    @Override
    public @NonNull View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        PauseRolesItem RoleItem = getItem(position);
        View viewToUse;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        //recyclable view has not been used yet, inflate it, store ViewHolder as a tag
        if(convertView == null) {
            viewToUse = inflater.inflate(R.layout.fragment_pause_roles_item, parent);

            holder = new ViewHolder();
            holder.roleImage = (ImageView) viewToUse.findViewById(R.id.role_img);
            holder.roleName = (CustomFontTextView) viewToUse.findViewById(R.id.role_name);
            viewToUse.setTag(holder);
        }
        //recyclable view already exists, use it
        else {
            viewToUse = convertView;
            holder = (ViewHolder) viewToUse.getTag();
        }

        if(RoleItem != null) {
            holder.roleName.setText(RoleItem.getRoleName());
            holder.roleImage.setImageResource(RoleItem.getImgName());
        }
        return viewToUse;
    }

}

package com.illum.MafiaRising;

//basic class for a list item in the Pause Roles activity
class PauseRolesItem {
    private String roleName;
    private int roleImg;
    //TODO: should this include another variable to overlay if player is dead?
    PauseRolesItem(String name, int img) {
        this.roleName = name;
        this.roleImg = img;
    }

    String getRoleName() {
        return roleName;
    }

    int getImgName() {
        return roleImg;
    }

    public void setRoleName(String name) {
        this.roleName = name;
    }

    public void setImgName(int img) {
        this.roleImg = img;
    }

}

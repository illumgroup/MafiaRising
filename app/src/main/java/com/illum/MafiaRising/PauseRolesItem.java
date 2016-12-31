package com.illum.MafiaRising;

public class PauseRolesItem {
    private String roleName;
    private int roleImg;

    public PauseRolesItem(String name, int img) {
        this.roleName = name;
        this.roleImg = img;
    }

    public String getRoleName() {
        return roleName;
    }

    public int getImgName() {
        return roleImg;
    }

    public void setRoleName(String name) {
        this.roleName = name;
    }

    public void setImgName(int img) {
        this.roleImg = img;
    }

}

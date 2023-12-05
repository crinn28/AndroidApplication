package com.example.crimeslostsreport.Models;

import java.io.Serializable;

public class UserRole implements Serializable {
    private int roleId;
    private String roleName;

    public UserRole() {
    }


    public UserRole(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}

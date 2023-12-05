package com.example.crimeslostsreport.Models;


import java.io.Serializable;

public class User implements Serializable {
    private int userId;
    private String username, password, email;
    private UserRole userRole;

    public User() {}

    public User(int userId, String username,String email, String password, UserRole userRole) {
        this.userId = userId;
        this.username = username;
        this.email=email;
        this.password = password;
        this.userRole = userRole;
    }

    public User(int userId, String username,String email, String password) {
        this.userId = userId;
        this.email=email;
        this.username = username;
        this.password = password;
    }

    public User(String username, String email, String password, UserRole userRole) {
        this.username = username;
        this.email=email;
        this.password = password;
        this.userRole = userRole;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

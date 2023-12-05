package com.example.crimeslostsreport.Classes;

import com.example.crimeslostsreport.Classes.Database.DBHelper;
import com.example.crimeslostsreport.Classes.Database.DBUser;
import com.example.crimeslostsreport.Models.User;

import java.util.Objects;

public class ChangePasswordHelper {

    DBUser dbUser;
    User user;

    public ChangePasswordHelper(User user) {
        dbUser = new DBUser();
        this.user = user;
    }

    public boolean isCorrectPassword(String currentPassword) {
        if (!Objects.equals(user.getPassword(), currentPassword))
            return false;
        return true;
    }

    public boolean matchPasswords(String newPassword, String confirmNewPassword) {
        if (!Objects.equals(newPassword, confirmNewPassword))
            return false;
        return true;
    }

    public void updatePassword(String newPassword) {
        try {
            dbUser.updatePassword(user, newPassword);
            user= dbUser.getUserById(user.getUserId());
        } catch (Exception exception) {
        }
    }

    public User getUser(){
        return user;
    }
}

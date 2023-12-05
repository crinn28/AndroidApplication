package com.example.crimeslostsreport.Classes;

import android.os.Build;

import com.example.crimeslostsreport.Classes.Database.DBHelper;
import com.example.crimeslostsreport.Classes.Database.DBUser;
import com.example.crimeslostsreport.Models.User;
import com.example.crimeslostsreport.Models.UserRole;

import java.util.ArrayList;

public class RegisterActivityHelper {

    private DBUser dbUser;
    ArrayList<UserRole> allRoles;
    private ArrayList<String> usernames;
    private UserRole currentUserRole;

    public RegisterActivityHelper() {

        dbUser = new DBUser();
        allRoles = dbUser.getAllRoles();
        usernames = dbUser.getAllUsernames();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            currentUserRole = allRoles.stream().filter(x -> x.getRoleName().equals("user")).findFirst().get();
        }
    }

    public boolean usernameAlreadyExists(String username) {
        if (!usernames.contains(username))
            return false;
        return true;
    }

    public void updateUserRole() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            currentUserRole = allRoles.stream().filter(x -> x.getRoleName().equals("admin")).findFirst().get();
        }
    }

    public void registerUser(String username, String email, String password) {
        User user = new User(username, email, password, currentUserRole);
        dbUser.addUser(user);

    }

    public boolean secretCodeIsValid(String secretCode) {
        if (dbUser.getSecretcode(secretCode).getSecretcodeId() == 0)
            return false;
        return true;
    }

}

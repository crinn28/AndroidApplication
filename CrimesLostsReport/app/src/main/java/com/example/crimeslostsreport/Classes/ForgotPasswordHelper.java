package com.example.crimeslostsreport.Classes;

import com.example.crimeslostsreport.Classes.Database.DBHelper;
import com.example.crimeslostsreport.Classes.Database.DBUser;
import com.example.crimeslostsreport.Models.User;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class ForgotPasswordHelper {

    private static final int MAX_LENGTH = 16;
    DBUser dbUser;

    public ForgotPasswordHelper() {
        dbUser = new DBUser();
    }

    public boolean isUsernameValid(String username) {
        ArrayList<String> usernames = dbUser.getAllUsernames();
        if (!usernames.contains(username))
            return false;
        return true;
    }

    public boolean isEmailLinkedWithUsername(String username, String email) {
        User user = dbUser.getUserByUsername(username);
        if (!Objects.equals(user.getEmail(), email))
            return false;
        return true;
    }

    public String generateNewPassword() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(MAX_LENGTH);
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    public void updatePassword(String username, String newPassword) {
        try {
            User user = dbUser.getUserByUsername(username);
            dbUser.updatePassword(user, newPassword);
        } catch (Exception exception) {

        }
    }
}

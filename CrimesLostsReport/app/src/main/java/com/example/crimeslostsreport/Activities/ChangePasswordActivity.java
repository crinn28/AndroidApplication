package com.example.crimeslostsreport.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crimeslostsreport.Classes.ChangePasswordHelper;
import com.example.crimeslostsreport.Models.User;
import com.example.crimeslostsreport.R;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextCurrentPassword, editTextNewPassword, editTextCofirmNewPassword;
    Button changePassword;
    User user;
    ChangePasswordHelper passwordHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        user = (User) getIntent().getSerializableExtra("user");
        passwordHelper = new ChangePasswordHelper(user);

        editTextCurrentPassword = findViewById(R.id.editTextCurrentPassword);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextCofirmNewPassword = findViewById(R.id.editTextCofirmNewPassword);

        changePassword = findViewById(R.id.buttonChangePassword);
        changePassword.setOnClickListener(this);
    }

    private void changePassword() {

        String currentPassword = editTextCurrentPassword.getText().toString();
        String newPassword = editTextNewPassword.getText().toString();
        String confirmNewPassword = editTextCofirmNewPassword.getText().toString();

        if (TextUtils.isEmpty(currentPassword)) {
            Toast.makeText(this, "Please enter current password", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(newPassword)) {
            Toast.makeText(this, "Please enter new password", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(confirmNewPassword)) {
            Toast.makeText(this, "Please confirm new password", Toast.LENGTH_LONG).show();
            return;
        }
        if (!passwordHelper.isCorrectPassword(currentPassword)) {
            Toast.makeText(this, "Current password is not correct", Toast.LENGTH_LONG).show();
            return;
        }

        if (!passwordHelper.matchPasswords(newPassword, confirmNewPassword)) {
            Toast.makeText(this, "Confirm new password != New password", Toast.LENGTH_LONG).show();
            return;
        }

        passwordHelper.updatePassword(confirmNewPassword);
        Toast.makeText(this, "Password changed successfully!", Toast.LENGTH_LONG).show();

        Intent profile = new Intent(this, ProfileActivity.class);
        profile.putExtra("user", passwordHelper.getUser());
        startActivity(profile);
        finish();
    }


    @Override
    public void onClick(View v) {
        if (v == changePassword) {
            changePassword();
        }
    }
}
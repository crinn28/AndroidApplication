package com.example.crimeslostsreport.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crimeslostsreport.Classes.ForgotPasswordHelper;
import com.example.crimeslostsreport.Classes.GMailSender;
import com.example.crimeslostsreport.R;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUsername, editTextEmail;
    private ForgotPasswordHelper passwordHelper;
    private Button buttonForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextUsername = findViewById(R.id.editTextUsername);
        buttonForgotPassword = findViewById(R.id.buttonForgotPassword);

        passwordHelper = new ForgotPasswordHelper();

        buttonForgotPassword.setOnClickListener(this);
    }

    private void forgotPassword() {

        String username = editTextUsername.getText().toString();
        String email = editTextEmail.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please enter username", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (!passwordHelper.isUsernameValid(username)) {
            Toast.makeText(this, "Invalid username", Toast.LENGTH_LONG).show();
            return;
        }

        if (!passwordHelper.isEmailLinkedWithUsername(username, email)) {
            Toast.makeText(this, "Invalid email", Toast.LENGTH_LONG).show();
            return;
        }

        String newPassword = passwordHelper.generateNewPassword();
        passwordHelper.updatePassword(username, newPassword);
        GMailSender gMailSender = new GMailSender(this, email,
                "New password", "Your password is:" + newPassword);
        gMailSender.execute();
        Toast.makeText(this, "Email sent with new password!", Toast.LENGTH_LONG).show();

        finish();

    }

    @Override
    public void onClick(View v) {
        if (v == buttonForgotPassword) {
            forgotPassword();

        }
    }
}
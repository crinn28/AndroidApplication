package com.example.crimeslostsreport.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crimeslostsreport.Classes.RegisterActivityHelper;
import com.example.crimeslostsreport.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private RegisterActivityHelper registerHelper;
    private ArrayList<String> usernames;
    private TextView textViewLogin;
    private EditText editTextUsername, editTextPassword, editTextConfirmPassword, editTextSecretCode,
            editTextEmail;
    private TextInputLayout helperSecretCode;
    private Button buttonRegister;
    private CheckBox adminAccount;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textViewLogin = (TextView) findViewById(R.id.textViewLogin);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);
        editTextSecretCode = (EditText) findViewById(R.id.editTextSecretCode);
        helperSecretCode = (TextInputLayout) findViewById(R.id.helperSecretCode);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        adminAccount = (CheckBox) findViewById(R.id.adminAccount);

        progressDialog = new ProgressDialog(this);

        textViewLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
        adminAccount.setOnClickListener(this);

        registerHelper = new RegisterActivityHelper();

    }

    private void registerUser() {

        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();
        String secretCode = editTextSecretCode.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please enter username", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (registerHelper.usernameAlreadyExists(username)) {
            Toast.makeText(this, "Username already exists. Type another one", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(confirmPassword) || !password.equals(confirmPassword)) {
            Toast.makeText(this, "Please confirm password", Toast.LENGTH_LONG).show();
            return;
        }
        if (adminAccount.isChecked()) {
            if (TextUtils.isEmpty(secretCode)) {
                Toast.makeText(this, "Please enter the authentication code", Toast.LENGTH_LONG).show();
                return;
            }
            if (!registerHelper.secretCodeIsValid(secretCode)) {
                Toast.makeText(this, "Invalid code! Please try again!", Toast.LENGTH_LONG).show();
                return;
            }
            registerHelper.updateUserRole();
        }
        progressDialog.setMessage("Registering User...");
        progressDialog.show();
        try {

            registerHelper.registerUser(username, email, password);
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        } catch (Exception ex) {

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonRegister:
                registerUser();
                break;
            case R.id.textViewLogin:
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.adminAccount:
                editTextSecretCode.setVisibility(View.INVISIBLE);
                helperSecretCode.setVisibility(View.INVISIBLE);
                if (adminAccount.isChecked()) {
                    editTextSecretCode.setVisibility(View.VISIBLE);
                    helperSecretCode.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
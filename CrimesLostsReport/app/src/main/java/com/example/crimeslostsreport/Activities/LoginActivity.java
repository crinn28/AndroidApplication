package com.example.crimeslostsreport.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crimeslostsreport.Classes.Database.DBHelper;
import com.example.crimeslostsreport.Classes.Database.DBUser;
import com.example.crimeslostsreport.Models.User;
import com.example.crimeslostsreport.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private DBUser dbUser;

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin, buttonRegister;
    private TextView forgotPassword;

    private ProgressDialog progressDialog;

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbUser = new DBUser();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        forgotPassword = findViewById(R.id.forgot_password);

        progressDialog = new ProgressDialog(this);

        forgotPassword.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
    }

    private void userLogin() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        User user = new User();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please enter username", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        user = dbUser.Login(username, password);
        if (user.getUserId() > 0) {
            progressDialog.setMessage("Logging Please Wait...");
            progressDialog.show();
            try {
                progressDialog.dismiss();
                Intent myactivity = new Intent(this, MainActivity.class);
                myactivity.putExtra("user", user);
                startActivity(myactivity);
                finish();
            } catch (Exception ex) {
                Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_LONG).show();
            return;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonLogin:
                userLogin();
                break;
            case R.id.buttonRegister:
                Intent myactivity = new Intent(this, RegisterActivity.class);
                startActivity(myactivity);
                break;
            case R.id.forgot_password:
                Intent forgotPassword = new Intent(this, ForgotPasswordActivity.class);
                startActivity(forgotPassword);
            default:
                break;
        }
    }
}
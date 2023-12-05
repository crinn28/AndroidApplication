package com.example.crimeslostsreport.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crimeslostsreport.Classes.Database.DBHelper;
import com.example.crimeslostsreport.Classes.Database.DBUser;
import com.example.crimeslostsreport.Models.User;
import com.example.crimeslostsreport.R;

public class ChangeEmailActivity extends AppCompatActivity implements View.OnClickListener {

    Button changeEmail;
    EditText newEmail;
    TextView textViewEmailAddress;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        user = (User) getIntent().getSerializableExtra("user");

        textViewEmailAddress = findViewById(R.id.textViewEmailAddress);
        textViewEmailAddress.setText(user.getEmail());

        newEmail = findViewById(R.id.editTextNewEmail);

        changeEmail = findViewById(R.id.buttonChangeEmail);
        changeEmail.setOnClickListener(this);

    }

    private void changeEmail() {

        String email = newEmail.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter an email", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            DBUser dbUser = new DBUser();
            dbUser.updateEmail(user, email);
            Toast.makeText(this, "Email changed successfully!", Toast.LENGTH_LONG).show();

            Intent profile = new Intent(this, ProfileActivity.class);
            profile.putExtra("user", dbUser.getUserById(user.getUserId()));
            startActivity(profile);
            finish();
        } catch (Exception exception) {
        }
    }

    @Override
    public void onClick(View v) {
        if (v == changeEmail) {
            changeEmail();
        }

    }
}
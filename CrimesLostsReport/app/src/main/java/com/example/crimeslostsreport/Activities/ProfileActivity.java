package com.example.crimeslostsreport.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.crimeslostsreport.Classes.ProfileActivityHelper;
import com.example.crimeslostsreport.Models.User;
import com.example.crimeslostsreport.R;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    TextView username, numberOfTotalReports, numberOfActiveReports, numberOfSolvedReports;
    ImageButton backButton;
    Button changeEmail, changePassword;
    User user;
    ProfileActivityHelper profileSetUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username = findViewById(R.id.username);
        numberOfTotalReports = (TextView) findViewById(R.id.number_of_total_reports);
        numberOfActiveReports = (TextView) findViewById(R.id.number_of_active_reports);
        numberOfSolvedReports = (TextView) findViewById(R.id.number_of_solved_reports);
        backButton = (ImageButton) findViewById(R.id.backButton);
        changePassword = (Button) findViewById(R.id.changePasswordProfile);
        changeEmail = (Button) findViewById(R.id.changeEmailProfile);

        user = (User) getIntent().getSerializableExtra("user");
        profileSetUp = new ProfileActivityHelper(user);
        user = profileSetUp.getUser();
        username.setText(user.getUsername());

        setUp();

        backButton.setOnClickListener(this);
        changePassword.setOnClickListener(this);
        changeEmail.setOnClickListener(this);

    }

    private void setUp() {
        profileSetUp = new ProfileActivityHelper(user);
        numberOfTotalReports.setText(profileSetUp.countTotalPostedReports());
        numberOfSolvedReports.setText(profileSetUp.countDoneReports());
        numberOfActiveReports.setText(profileSetUp.countInProgressReports());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backButton:
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
                break;
            case R.id.changePasswordProfile:
                Intent changePassword = new Intent(this, ChangePasswordActivity.class);
                changePassword.putExtra("user", user);
                startActivity(changePassword);
                finish();
                break;
            case R.id.changeEmailProfile:
                Intent changeEmail = new Intent(this, ChangeEmailActivity.class);
                changeEmail.putExtra("user", user);
                startActivity(changeEmail);
                finish();
                break;
        }

    }
}
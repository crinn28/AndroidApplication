package com.example.crimeslostsreport.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crimeslostsreport.Classes.Database.DBAnnouncement;
import com.example.crimeslostsreport.Classes.Database.DBHelper;
import com.example.crimeslostsreport.Classes.Database.DBUser;
import com.example.crimeslostsreport.Fragments.MapFragment;
import com.example.crimeslostsreport.Models.Announcement;
import com.example.crimeslostsreport.Models.AnnouncementLocation;
import com.example.crimeslostsreport.Models.User;
import com.example.crimeslostsreport.R;

public class SelectedItemActivity extends AppCompatActivity implements View.OnClickListener {

    AnnouncementLocation location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_selected_item);

        int id = getIntent().getIntExtra("Id", 0);
        DBUser dbUser = new DBUser();
        DBAnnouncement dbAnnouncement= new DBAnnouncement();

        Announcement announcement = dbAnnouncement.getAnnouncement(id);
        location = dbAnnouncement.getAnnouncementLocation(id);
        User user = dbUser.getUserById(announcement.getUser_id());

        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        ImageView imageView = findViewById(R.id.imageView);
        TextView date = findViewById(R.id.dateTextView);
        TextView name = findViewById(R.id.nameTextView);
        ImageButton backButton = findViewById(R.id.backButton);
        Button viewLocation = (Button) findViewById(R.id.viewLocation);

        backButton.setOnClickListener(this);
        viewLocation.setOnClickListener(this);

        titleTextView.setText(announcement.getTitle());
        descriptionTextView.setText(announcement.getDescription());

        byte[] byteArr = announcement.getImageUrl();
        if (byteArr != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArr, 0, byteArr.length);
            if (bmp != null)
                imageView.setImageBitmap(bmp);
        }

        date.setText(announcement.getDate().toString());
        name.setText(user.getUsername());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backButton:
                Intent intent = new Intent(SelectedItemActivity.this, MainActivity.class);
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                startActivity(intent);
                finish();
                break;
            case R.id.viewLocation:
                Fragment fragment = new MapFragment(new User(), 0, location.getLatitude(), location.getLongitude(),
                        View.INVISIBLE);
                getSupportFragmentManager()
                        .beginTransaction().replace(R.id.frame_layout, fragment)
                        .commit();
                break;
            default:
                break;

        }
    }
}
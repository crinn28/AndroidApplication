package com.example.crimeslostsreport.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import com.example.crimeslostsreport.Classes.Database.DBAnnouncement;
import com.example.crimeslostsreport.Classes.Database.DBHelper;
import com.example.crimeslostsreport.Fragments.MapFragment;
import com.example.crimeslostsreport.Models.Announcement;
import com.example.crimeslostsreport.Models.AnnouncementLocation;
import com.example.crimeslostsreport.Models.User;
import com.example.crimeslostsreport.R;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        DBAnnouncement dbAnnouncement=new DBAnnouncement();
        int announcementID = getIntent().getIntExtra("announcementID", 0);
        User user =(User)getIntent().getSerializableExtra("user");

        Announcement announcement = dbAnnouncement.getAnnouncement(announcementID);
        AnnouncementLocation location = dbAnnouncement.getAnnouncementLocation(announcementID);

        Fragment fragment=new MapFragment(user, announcementID,location.getLatitude(),location.getLongitude(),
                View.VISIBLE);
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.frame_layout,fragment)
                .commit();

    }
}
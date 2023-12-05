package com.example.crimeslostsreport.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.crimeslostsreport.Adapters.AnnouncementAdapter;
import com.example.crimeslostsreport.Classes.Database.DBAnnouncement;
import com.example.crimeslostsreport.Classes.Database.DBHelper;
import com.example.crimeslostsreport.Classes.FilterAnnouncements;
import com.example.crimeslostsreport.Interfaces.RecyclerViewInterface;
import com.example.crimeslostsreport.Models.Announcement;
import com.example.crimeslostsreport.Models.AnnouncementLocation;
import com.example.crimeslostsreport.Models.User;
import com.example.crimeslostsreport.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RecyclerViewInterface,
        PopupMenu.OnMenuItemClickListener {

    private ArrayList<Announcement> announcements = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private AnnouncementAdapter adapter;
    private DBAnnouncement dbAnnouncement;
    User currentUser;
    Handler handler;
    private ImageButton addButton, menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentUser = (User) getIntent().getSerializableExtra("user");
        addButton = (ImageButton) findViewById(R.id.addButton);
        menuButton = (ImageButton) findViewById(R.id.menuButton);
        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        dbAnnouncement = new DBAnnouncement();

        setUp();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);

        adapter = new AnnouncementAdapter(currentUser, this, announcements, this);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addButton.setOnClickListener(this);
        menuButton.setOnClickListener(this);

        try {
            SearchView searchView = findViewById(R.id.searchView);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filter(newText);
                    return false;
                }
            });
        } catch (Exception e) {
        }

        new Thread() {
            public void run() {
                try {
                    while (true) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setUp();
                                adapter.updateList(announcements);
                            }
                        });
                        Thread.sleep(2 * 60 * 1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                setUp();
                adapter.updateList(announcements);
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                Intent myActivity = new Intent(MainActivity.this, ProfileActivity.class);
                myActivity.putExtra("user", currentUser);
                startActivity(myActivity);
                return true;
            case R.id.tips:
                startActivity(new Intent(MainActivity.this, TipsActivity.class));
                return true;
            case R.id.logout:
                finish();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                return true;
            default:
                return false;
        }
    }

    private void setUp() {
        announcements = dbAnnouncement.GetAnnouncements();
    }

    private void filter(String text) {
        FilterAnnouncements filterAnnouncements = new FilterAnnouncements();
        ArrayList<Announcement> filteredList = filterAnnouncements.filterByTitle(announcements, text);

        if (filterAnnouncements.filterByTitle(announcements, text).isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            adapter.filterList(filteredList);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addButton:
                Intent myActivity = new Intent(MainActivity.this, AnnouncementActivity.class);
                myActivity.putExtra("user", currentUser);
                startActivity(myActivity);
                break;
            case R.id.menuButton:
                PopupMenu popup = new PopupMenu(this, view);
                popup.setOnMenuItemClickListener(this);
                popup.inflate(R.menu.navigation_items);
                popup.show();
                break;
        }
    }

    @Override
    public void onDeleteClick(int position) {
        Announcement announcement = announcements.get(position);
        announcement.setIs_deleted(true);
        AnnouncementLocation location = dbAnnouncement.getAnnouncementLocation(announcement.getAnnouncement_id());
        dbAnnouncement.updateAnnouncement(announcement, location.getLatitude(), location.getLongitude());
        announcements.remove(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void onEditClick(int position) {
        Announcement announcement = announcements.get(position);
        Intent myActivity = new Intent(MainActivity.this, AnnouncementActivity.class);
        myActivity.putExtra("user", currentUser);
        myActivity.putExtra("announcementID", announcement.getAnnouncement_id());
        startActivity(myActivity);
    }

    @Override
    public void onItemClick(int position) {
        try {
            Intent intent = new Intent(this, SelectedItemActivity.class);
            intent.putExtra("user", currentUser);
            intent.putExtra("Id", announcements.get(position).getAnnouncement_id());
            startActivity(intent);
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
package com.example.crimeslostsreport.Classes;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Spinner;

import com.example.crimeslostsreport.Classes.Database.DBAnnouncement;
import com.example.crimeslostsreport.Classes.Database.DBHelper;
import com.example.crimeslostsreport.Classes.Database.DBStatus;
import com.example.crimeslostsreport.Models.Announcement;
import com.example.crimeslostsreport.Models.Status;
import com.example.crimeslostsreport.Models.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AnnouncementActivityHelper {

    private List<Status> statusList;
    private List<String> statuses;
    private byte[] byteArray;
    private DBAnnouncement dbAnnouncement;
    private DBStatus dbStatus;
    private User currentUser;
    private Announcement announcement;
    private int announcementID;

    public AnnouncementActivityHelper(User user, int announcementID) {

        dbAnnouncement = new DBAnnouncement();
        dbStatus = new DBStatus();
        currentUser = user;
        this.announcementID = announcementID;
        announcement = dbAnnouncement.getAnnouncement(announcementID);
        byteArray = new byte[0];
    }

    public List<String> setStatuses() {
        List<String> statuses = new ArrayList<>();
        statusList = dbStatus.getStatuses();
        for (Status s : statusList) {
            statuses.add(s.getStatusName());
        }
        this.statuses = statuses;
        return statuses;
    }

    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    public List<String> setCategories() {
        List<String> categories = new ArrayList<>();
        categories.add("LOST");
        if (currentUser.getUserRole().getRoleName().equals("admin")) {
            categories.add("CRIME");
        }
        return categories;
    }

    public int setVisibility() {
        if (announcementID != 0) {
            if (currentUser.getUserRole().getRoleName().equals("admin")) {
                return View.VISIBLE;
            }
        }
        return View.INVISIBLE;
    }

    public String setButtonText() {
        if (announcementID != 0) {
            return "UPDATE";
        }
        return "POST";
    }

    public Announcement getCurrentAnnouncement() {
        if (announcementID != 0)
            return announcement;
        return new Announcement();
    }

    public Bitmap getAnnouncementImage() {
        byte[] byteArr = announcement.getImageUrl();
        if (byteArr != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArr, 0, byteArr.length);
            if (bmp != null)
                return bmp;
        }
        return null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public int getAnnouncementID() {
        return announcementID;
    }

    public void updateAnnouncement(String title, String description, Boolean isCrime, Spinner status,
                                   byte[] imageCovnersion, double latitude, double longitude) {

        announcement.setTitle(title);
        announcement.setDescription(description);
        announcement.setIs_crime(isCrime);
        String selectedStatus = status.getSelectedItem().toString();
        announcement.setStatus(statusList.get(statuses.indexOf(selectedStatus)));
        if (imageCovnersion.length != 0)
            announcement.setImageUrl(imageCovnersion);
        if (byteArray.length != 0)
            announcement.setImageUrl(byteArray);

        dbAnnouncement.updateAnnouncement(announcement, latitude, longitude);
    }

    public void addAnnouncement(byte[] image, String description, String title, boolean selectedCategory,
                                double latitude, double longitude) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        String date = sdf.format(c.getTime());

        announcement = new Announcement(currentUser.getUserId(), image, description,
                new java.sql.Date(Calendar.getInstance().getTime().getTime()), title, selectedCategory,
                new Boolean("False"), new Status(0, "new"));

        dbAnnouncement.addAnnouncement(announcement, latitude, longitude);
    }

    public byte[] convertImageUriToBinaryArray(Uri imageUri, ContentResolver contentResolver) {
        Bitmap bmp = null;
        byte[] byteArray = new byte[0];
        if (imageUri != null) {
            try {
                bmp = MediaStore.Images.Media.getBitmap(contentResolver, imageUri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray = stream.toByteArray();
                bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return byteArray;
    }

}


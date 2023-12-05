package com.example.crimeslostsreport.Models;

public class AnnouncementLocation {
    private int announcementId;
    private double latitude;
    private double longitude;

    public AnnouncementLocation() {
    }

    public AnnouncementLocation(int announcementId, double latitude, double longitude) {
        this.announcementId = announcementId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(int announcementId) {
        this.announcementId = announcementId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

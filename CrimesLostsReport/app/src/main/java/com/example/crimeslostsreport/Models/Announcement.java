package com.example.crimeslostsreport.Models;

import java.io.Serializable;
import java.sql.Date;

public class Announcement implements Serializable {
    private int annoncement_id;
    private int user_id;
    private byte[] imageUrl;
    private String description;
    private Date date;
    private String title;
    private Boolean is_crime;
    private Boolean is_deleted;
    private Status status;

    public Announcement(int announcement_id, int user_id, byte[] imageUrl,
                        String description, Date date, String title, Boolean is_crime, Boolean is_deleted,
                        Status status) {
        this.annoncement_id = announcement_id;
        this.user_id = user_id;
        this.imageUrl = imageUrl;
        this.description = description;
        this.date = date;
        this.title = title;
        this.is_crime = is_crime;
        this.is_deleted = is_deleted;
        this.status = status;
    }

    public Announcement() {
    }

    public Announcement(int user_id, byte[] imageUrl, String description, Date date, String title, Boolean is_crime, Boolean is_deleted, Status status) {
        this.user_id = user_id;
        this.imageUrl = imageUrl;
        this.description = description;
        this.date = date;
        this.title = title;
        this.is_crime = is_crime;
        this.is_deleted = is_deleted;
        this.status = status;
    }

    public int getAnnouncement_id() {
        return annoncement_id;
    }

    public void setAnnouncement_id(int announcement_id) {
        this.annoncement_id = announcement_id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public byte[] getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(byte[] imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getIs_crime() {
        return is_crime;
    }

    public void setIs_crime(Boolean is_crime) {
        this.is_crime = is_crime;
    }

    public Boolean getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.is_deleted = is_deleted;
    }
}

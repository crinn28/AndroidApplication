package com.example.crimeslostsreport.Classes.Database;

import com.example.crimeslostsreport.Models.Announcement;
import com.example.crimeslostsreport.Models.AnnouncementLocation;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class DBAnnouncement extends DBHelper {

    Connection connection;
    DBStatus dbStatus;

    public DBAnnouncement() {
        connection = super.connection;
        dbStatus = new DBStatus();
    }

    public ArrayList<Announcement> GetAnnouncements() {
        ArrayList<Announcement> announcements = new ArrayList<>();

        try {
            CallableStatement statement = this.connection.prepareCall("call get_announcements()");

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Announcement announcement = new Announcement(result.getInt("announcement_id"),
                        result.getInt("user_id"),
                        result.getBytes("image"),
                        result.getString("description"),
                        result.getDate("date"),
                        result.getString("title"),
                        result.getBoolean("is_crime"),
                        result.getBoolean("is_deleted"),
                        dbStatus.getStatus(result.getInt("status_id")));
                if (!announcement.getIs_deleted())
                    announcements.add(announcement);

            }
            statement.close();

        } catch (SQLException e) {
            System.out.print("Error!");
            e.printStackTrace();
        }

        return announcements;
    }

    public Announcement getAnnouncement(int announcement_id) {
        Announcement announcement = new Announcement();

        try {
            CallableStatement statement = this.connection.prepareCall("call get_announcement(?)");

            statement.setInt(1, announcement_id);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                announcement = new Announcement(result.getInt("announcement_id"),
                        result.getInt("user_id"),
                        result.getBytes("image"),
                        result.getString("description"),
                        result.getDate("date"),
                        result.getString("title"),
                        result.getBoolean("is_crime"),
                        result.getBoolean("is_deleted"),
                        dbStatus.getStatus(result.getInt("status_id")));
            }
            statement.close();

        } catch (SQLException e) {
            System.out.print("Error!");
            e.printStackTrace();
        }

        return announcement;
    }

    public ArrayList<Announcement> getUserAnnouncements(int user_id) {
        ArrayList<Announcement> announcements = new ArrayList<>();

        try {
            CallableStatement statement = this.connection.prepareCall("call get_user_announcements(?)");

            statement.setInt(1, user_id);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Announcement announcement = new Announcement(result.getInt("announcement_id"),
                        result.getInt("user_id"),
                        result.getBytes("image"),
                        result.getString("description"),
                        result.getDate("date"),
                        result.getString("title"),
                        result.getBoolean("is_crime"),
                        result.getBoolean("is_deleted"),
                        dbStatus.getStatus(result.getInt("status_id")));
                if (announcement.getUser_id() == user_id)
                    announcements.add(announcement);
            }
            statement.close();

        } catch (SQLException e) {
            System.out.print("Error!");
            e.printStackTrace();
        }

        return announcements;
    }

    public void addAnnouncementLocation(AnnouncementLocation announcementLocation) {
        try {
            CallableStatement statement = this.connection.prepareCall("call add_announcement_location(?, ?, ?)");

            statement.setInt(1, announcementLocation.getAnnouncementId());
            statement.setDouble(2, announcementLocation.getLatitude());
            statement.setDouble(3, announcementLocation.getLongitude());

            statement.execute();
            statement.close();

        } catch (SQLException e) {
            System.out.print("Error!");
            e.printStackTrace();
        }
    }

    public void updateAnnouncementLocation(Announcement announcement, double latitude, double longitude) {

        try {
            CallableStatement statement =
                    this.connection.prepareCall("call update_announcement_location(?, ?, ?)");

            statement.setInt(1, announcement.getAnnouncement_id());
            statement.setDouble(2, latitude);
            statement.setDouble(3, longitude);

            statement.execute();
            statement.close();

        } catch (SQLException e) {
            System.out.print("Error!");
            e.printStackTrace();
        }
    }

    public void addAnnouncement(Announcement announcement, double latitude, double longitude) {
        int id = 0;
        try {

            CallableStatement statement = this.connection.prepareCall("call add_announcement(?, ?, ?, ?, ?, ?, ?)");

            statement.setInt(1, announcement.getUser_id());
            statement.setString(2, announcement.getDescription());
            statement.setDate(3, (Date) announcement.getDate());
            statement.setString(4, announcement.getTitle());
            statement.setBoolean(5, announcement.getIs_crime());
            statement.setBytes(6, announcement.getImageUrl());
            statement.registerOutParameter(7, Types.INTEGER);

            statement.executeUpdate();

            id = statement.getInt(7);

            addAnnouncementLocation(new AnnouncementLocation(id, latitude, longitude));

            statement.close();
        } catch (SQLException e) {
            System.out.print("Error!");
            e.printStackTrace();
        }

    }

    public void updateAnnouncement(Announcement announcement, double latitude, double longitude) {
        try {
            CallableStatement statement =
                    this.connection.prepareCall("call update_announcement(?, ?, ?, ?, ?, ?, ?, ?)");

            statement.setInt(1, announcement.getAnnouncement_id());
            statement.setString(2, announcement.getDescription());
            statement.setDate(3, announcement.getDate());
            statement.setString(4, announcement.getTitle());
            statement.setBoolean(5, announcement.getIs_crime());
            statement.setBytes(6, announcement.getImageUrl());
            statement.setBoolean(7, announcement.getIs_deleted());
            statement.setInt(8, announcement.getStatus().getStatusId());

            updateAnnouncementLocation(announcement, latitude, longitude);
            statement.execute();
            statement.close();

        } catch (SQLException e) {
            System.out.print("Error!");
            e.printStackTrace();
        }
    }

    public AnnouncementLocation getAnnouncementLocation(int announcementID) {
        AnnouncementLocation location = new AnnouncementLocation();
        try {
            CallableStatement statement = this.connection.prepareCall("call get_announcement_location(?)");

            statement.setInt(1, announcementID);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                location = new AnnouncementLocation(announcementID,
                        result.getDouble("latitude"),
                        result.getDouble("longitude"));
            }
            statement.close();


        } catch (SQLException e) {
            System.out.print("Error!");
            e.printStackTrace();
        }

        return location;

    }
}

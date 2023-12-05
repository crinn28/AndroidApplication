package com.example.crimeslostsreport.Classes;

import android.os.Build;

import com.example.crimeslostsreport.Classes.Database.DBAnnouncement;
import com.example.crimeslostsreport.Classes.Database.DBHelper;
import com.example.crimeslostsreport.Classes.Database.DBStatus;
import com.example.crimeslostsreport.Classes.Database.DBUser;
import com.example.crimeslostsreport.Models.Announcement;
import com.example.crimeslostsreport.Models.Status;
import com.example.crimeslostsreport.Models.User;

import java.util.ArrayList;
import java.util.Objects;

public class ProfileActivityHelper {

    DBUser dbUser;
    DBAnnouncement dbAnnouncement;
    DBStatus dbStatus;
    User user;
    ArrayList<Status> statuses;
    ArrayList<Announcement> userAnnouncements;

    public ProfileActivityHelper(User user) {
        dbUser = new DBUser();
        dbAnnouncement = new DBAnnouncement();
        dbStatus=new DBStatus();
        this.user=user;
        statuses = dbStatus.getStatuses();
        userAnnouncements = dbAnnouncement.getUserAnnouncements(user.getUserId());
    }

    public String countTotalPostedReports() {
        return String.valueOf(userAnnouncements.size());
    }

    public String countDoneReports() {

        Status doneStatus = new Status();
        long doneReports = 0L;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            doneStatus = (Status) statuses.stream().filter(
                    status -> Objects.equals(status.getStatusName(), "DONE")).findAny().get();

            Status finalDoneStatus = doneStatus;

            doneReports = userAnnouncements.stream().filter(
                    announcement -> announcement.getStatus().getStatusId() == finalDoneStatus.getStatusId()).count();
        }

        return Long.toString(doneReports);
    }

    public String countInProgressReports() {

        Status inProgressStatus = new Status();
        long inProgressReports = 0L;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            inProgressStatus = (Status) statuses.stream().filter(
                    status -> Objects.equals(status.getStatusName(), "IN PROGRESS")).findAny().get();

            Status finalInProgressStatus = inProgressStatus;

            inProgressReports = userAnnouncements.stream().filter(
                    announcement -> announcement.getStatus().getStatusId() == finalInProgressStatus.getStatusId()).count();
        }
        return Long.toString(inProgressReports);
    }

    public User getUser() {
        return dbUser.getUserById(user.getUserId());
    }
}

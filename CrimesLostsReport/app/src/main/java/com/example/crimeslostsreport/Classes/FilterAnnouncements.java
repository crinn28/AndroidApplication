package com.example.crimeslostsreport.Classes;

import com.example.crimeslostsreport.Models.Announcement;

import java.util.ArrayList;

public class FilterAnnouncements {

    public ArrayList<Announcement> filterByTitle(ArrayList<Announcement> announcements, String title){
        ArrayList<Announcement> filteredList = new ArrayList<Announcement>();

        for (Announcement item : announcements) {

            if (item.getTitle().toLowerCase().contains(title.toLowerCase())) {
                filteredList.add(item);
            }
        }
        return filteredList;
    }
}

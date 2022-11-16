package com.ntobeko.confmanagement.models;

public class Conference {
    private String name, theme, venue, date, createdDate;

    public Conference(String name, String theme, String venue, String date, String createdDate) {
        this.name = name;
        this.theme = theme;
        this.venue = venue;
        this.date = date;
        this.createdDate = createdDate;
    }

    public Conference(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}

package com.ntobeko.confmanagement.models;

public class Abstract {
    private String image, name, description, date, offer;

    public Abstract(String image, String name, String description, String date, String offer) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.date = date;
        this.offer = offer;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getOffer() {
        return offer;
    }
}

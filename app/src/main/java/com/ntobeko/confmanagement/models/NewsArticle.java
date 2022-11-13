package com.ntobeko.confmanagement.models;

import com.google.type.DateTime;

public class NewsArticle {
    private final String title;
    private final String description;
    private final String originatorId;
    private final String datePosted;
    private final String link;

    public NewsArticle(String title, String description, String originatorId, String datePosted,String link) {
        this.title = title;
        this.description = description;
        this.originatorId = originatorId;
        this.datePosted = datePosted;
        this.link = link;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getOriginatorId() {
        return originatorId;
    }
    public String getLink() {
        return link;
    }
    public String getDatePosted() {
        return datePosted;
    }
}

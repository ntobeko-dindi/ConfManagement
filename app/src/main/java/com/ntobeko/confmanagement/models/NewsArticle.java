package com.ntobeko.confmanagement.models;

public class NewsArticle {
    private final String title;
    private final String description;

    public NewsArticle(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}

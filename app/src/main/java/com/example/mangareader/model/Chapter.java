package com.example.mangareader.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "chapters_table")
public class Chapter {
    @NonNull
    private int number;
    @NonNull
    private long date;
    @NonNull
    private String title;
    @NonNull
    @PrimaryKey
    private String id;
    private String description;

    public Chapter(int number, long date, @NonNull String title, @NonNull String id, String description) {
        this.number = number;
        this.date = date;
        this.title = title;
        this.id = id;
        this.description = description;
    }

    public int getNumber() {
        return number;
    }

    public long getDate() {
        return date;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}

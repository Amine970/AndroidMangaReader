package com.example.mangareader.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "chapters_table")
public class Chapter {
    @NonNull
    @PrimaryKey
    private String id;
    @NonNull
    private int number;
    @NonNull
    private long date;
    @NonNull
    private String title;
    @NonNull
    private String mangaId;
    public Chapter(int number, long date, @NonNull String title, @NonNull String id, @NonNull String mangaId) {
        this.number = number;
        this.date = date;
        this.title = title;
        this.id = id;
        this.mangaId = mangaId;
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

    @NonNull
    public String getMangaId() {
        return mangaId;
    }
}

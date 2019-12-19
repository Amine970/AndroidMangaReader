package com.example.mangareader.model.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "chapters_table")
public class Chapter {
    @NonNull
    @PrimaryKey
    private String id;
    @NonNull
    private String number;
    @NonNull
    private long date;
    @NonNull
    private String title;
    @NonNull
    private String mangaId;
    private long hits;
    private String[] category;

    public String[] getCategory() {
        return category;
    }

    public void setCategory(String[] category) {
        this.category = category;
    }

    @NonNull
    public String getMangaTitle() {
        return mangaTitle;
    }

    public void setMangaTitle(@NonNull String mangaTitle) {
        this.mangaTitle = mangaTitle;
    }

    @NonNull
    private String mangaTitle;
    public Chapter(String number, long date, @NonNull String title, @NonNull String id, @NonNull String mangaId, long hits, String[] category) {
        this.number = number;
        this.date = date;
        this.title = title;
        this.id = id;
        this.mangaId = mangaId;
        this.category = category;
    }
    public Chapter(List<String> chap, @NonNull String mangaId) {
        //int nonDigitsChapDate = chap.get(1).replaceAll("[^0-9]", "").length();
        //int nonDigitsChapNum = chap.get(0).replaceAll("[^0-9]", "").length();
        try {
            this.number =chap.get(0);
            this.date = Long.parseLong(chap.get(1));
            this.title = chap.get(2);
            this.id = chap.get(3);
            this.mangaId = mangaId;
        } catch(Exception e) {
            Log.i("debugging", "Chapter: " + chap.get(2) + " not inserted ");
        }
    }
    public String getNumber() {
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

    public long getHits() {
        return hits;
    }

    public void setHits(long hits) {
        this.hits = hits;
    }
}

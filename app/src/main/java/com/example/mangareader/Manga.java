package com.example.mangareader;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "mangas_table")
public class Manga {
    @PrimaryKey
    @NonNull
    @SerializedName("i")
    private String id;
    @NonNull
    @SerializedName("im")
    private String image;
    @NonNull
    @SerializedName("t")
    private String title;
    @NonNull
    @SerializedName("c")
    private String[] category;
    @NonNull
    @SerializedName("ld")
    private long lastChapterDate;
    @NonNull
    @SerializedName("h")
    private long hits;
    public Manga(@NonNull String id, @NonNull String image, @NonNull String title, @NonNull String[] category, long lastChapterDate, long hits) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.category = category;
        this.lastChapterDate = lastChapterDate;
        this.hits = hits;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getImage() {
        return image;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String[] getCategory() {
        return category;
    }

    public long getLastChapterDate() {
        return lastChapterDate;
    }

    public long getHits() {
        return hits;
    }
}

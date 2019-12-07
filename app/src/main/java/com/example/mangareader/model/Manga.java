package com.example.mangareader.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "mangas_table")
public class Manga implements Comparable<Manga>{
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
    @NonNull
    @Ignore
    private List<Chapter> mangaChapters;
    @NonNull
    @Ignore
    @SerializedName("chapters")
    private List<List<String>> chapters;
    @NonNull
    private String author;
    @NonNull
    private String description;
    @NonNull
    private int released;   // année
    public Manga(@NonNull String id, @NonNull String image, @NonNull String title, @NonNull String[] category, long lastChapterDate, long hits) {
        this.id = id;
        this.image = "https://cdn.mangaeden.com/mangasimg/" + image;
        this.title = title;
        this.category = category;
        this.lastChapterDate = lastChapterDate;
        this.hits = hits;
    }
    public Manga(@NonNull String id, @NonNull String image, @NonNull String title, @NonNull String[] category, long lastChapterDate, long hits, List<List<String>> chapters) {
        this.id = id;
        this.image = "https://cdn.mangaeden.com/mangasimg/" + image;
        this.title = title;
        this.category = category;
        this.lastChapterDate = lastChapterDate;
        this.hits = hits;
        this.chapters = chapters;
    }
    public void setMangaChapters1(@NonNull List<List<String>> chapters) {
        List<Chapter> listChapters = new ArrayList<>();

        for(List<String> chap : chapters) {
            Boolean good = true;
            for(String x : chap)
                if(x == null)
                    good = false;
            String chapNum = chap.get(0).replaceAll("[^0-9].*", "");
            String chapDate =  chap.get(1).replaceAll("[^0-9].*", "");
            if(good)
                listChapters.add(new Chapter(Integer.parseInt(chapNum),Long.parseLong(chapDate), chap.get(2),chap.get(3), id));
        }
        this.mangaChapters = listChapters;
    }

    public void setAuthor(@NonNull String author) {
        this.author = author;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public String getAuthor() {
        return author;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public int getReleased() {
        return released;
    }

    public void setReleased(int released) {
        this.released = released;
    }

    @NonNull
    public List<Chapter> getMangaChapters() {
        return mangaChapters;
    }
    @NonNull
    public List<List<String>> getChapters() {
        return chapters;
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

    @Override
    public int compareTo(Manga o) {
        return Long.compare(o.hits, this.hits);
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setImage(@NonNull String image) {
        this.image = image;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setCategory(@NonNull String[] category) {
        this.category = category;
    }

    public void setLastChapterDate(long lastChapterDate) {
        this.lastChapterDate = lastChapterDate;
    }

    public void setHits(long hits) {
        this.hits = hits;
    }

    public void setMangaChapters(@NonNull List<Chapter> mangaChapters) {
        this.mangaChapters = mangaChapters;
    }

    public void setChapters(@NonNull List<List<String>> chapters) {
        this.chapters = chapters;
    }
}

package com.example.mangareader.model.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pages_table")
public class Page implements Comparable<Page> {

    @NonNull
    private int pageNumber;
    @NonNull
    @PrimaryKey
    private String imageURL;
    @NonNull String chapterID;
    public Page(int pageNumber, @NonNull String imageURL, @NonNull String chapterID) {
        this.pageNumber = pageNumber;
        if(imageURL.contains("https://cdn.mangaeden.com/mangasimg"))
            this.imageURL =  imageURL;  //
        else
            this.imageURL = "https://cdn.mangaeden.com/mangasimg/" + imageURL;
        this.chapterID = chapterID;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @NonNull
    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(@NonNull String imageURL) {
        this.imageURL = imageURL;
    }

    @NonNull
    public String getChapterID() {
        return chapterID;
    }

    public void setChapterID(@NonNull String chapterID) {
        this.chapterID = chapterID;
    }

    @Override
    public int compareTo(Page o) {
        return Integer.compare(pageNumber, o.pageNumber);
    }
}

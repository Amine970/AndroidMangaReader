package com.example.mangareader.model.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChapterDetails {
    private List<Page> chapterPages;
    @SerializedName("images")
    private List<List<String>> rawPages;    // 0 -> numéro de page, 1 -> image
    public ChapterDetails(List<Page> pages) {
        this.chapterPages = pages;
    }

    public List<Page> getChapterPages() {
        return chapterPages;
    }

    public void setChapterPages(List<Page> chapterPages) {
        this.chapterPages = chapterPages;
    }

    public List<List<String>> getRawPages() {
        return rawPages;
    }

    public void setRawPages(List<List<String>> rawPages) {
        this.rawPages = rawPages;
    }
}

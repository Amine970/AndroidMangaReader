package com.example.mangareader.model;

import java.util.List;

public class MangaDetails {
    private List<List<String>> chapters;

    public MangaDetails(List<List<String>> chapters) {
        this.chapters = chapters;
    }

    public List<List<String>> getChapters() {
        return chapters;
    }

    public void setChapters(List<List<String>> chapters) {
        this.chapters = chapters;
    }
}

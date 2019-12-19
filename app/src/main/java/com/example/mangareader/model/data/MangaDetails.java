package com.example.mangareader.model.data;

import java.util.List;

public class MangaDetails {
    private List<List<String>> chapters;
    private String author;
    private String description;

    public MangaDetails(List<List<String>> chapters, String author, String description) {
        this.chapters = chapters;
        this.author = author;
        this.description = description;
    }

    public List<List<String>> getChapters() {
        return chapters;
    }

    public void setChapters(List<List<String>> chapters) {
        this.chapters = chapters;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

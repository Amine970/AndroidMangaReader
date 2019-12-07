package com.example.mangareader.model;

import java.util.List;

public class AllMangas {
    private int end;
    private List<Manga> manga;
    private int page;
    private int start;

    public AllMangas(int end, List<Manga> manga, int page, int start){//, int total) {
        this.end = end;
        this.manga = manga;
        this.page = page;
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public List<Manga> getManga() {
        return manga;
    }

    public void setManga(List<Manga> manga) {
        this.manga = manga;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

}

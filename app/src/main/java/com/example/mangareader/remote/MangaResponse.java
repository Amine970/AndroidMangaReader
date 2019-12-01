package com.example.mangareader.remote;

import com.example.mangareader.Manga;

public class MangaResponse {
    private int end;
    private Manga[] manga;
    private int page;
    private int start;
    private int total;

    public MangaResponse(int end, Manga[] manga, int page, int start, int total) {
        this.end = end;
        this.manga = manga;
        this.page = page;
        this.start = start;
        this.total = total;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public Manga[] getManga() {
        return manga;
    }

    public void setManga(Manga[] manga) {
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

package com.example.mangareader.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mangareader.model.data.Chapter;
import com.example.mangareader.model.repository.ChaptersRepository;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class ChaptersViewModel extends AndroidViewModel {

    private LiveData<List<Chapter>> recentChapters;
    private ChaptersRepository chaptersRepository;
    private LiveData<List<Chapter>> chaptersByMangaID;
    public ChaptersViewModel(@NonNull Application application, String mangaID) {
        super(application);
        chaptersRepository = new ChaptersRepository(application, mangaID);
        recentChapters = chaptersRepository.getRecentChapters();
        chaptersByMangaID = chaptersRepository.getChaptersByMangaID();
    }

    public LiveData<List<Chapter>> getRecentChapters() {
        return recentChapters;
    }

    public LiveData<List<Chapter>> getChaptersByMangaID() {
        return chaptersByMangaID;
    }

    public CompositeDisposable getDisposables() {
        return chaptersRepository.recentChaptersCompositeDisposable;
    }

}

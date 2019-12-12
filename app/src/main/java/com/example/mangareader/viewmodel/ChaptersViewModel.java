package com.example.mangareader.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mangareader.model.Chapter;
import com.example.mangareader.model.repository.ChaptersRepository;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class ChaptersViewModel extends AndroidViewModel {

    private LiveData<List<Chapter>> recentChapters;
    private LiveData<List<Chapter>> allChapters;
    private ChaptersRepository chaptersRepository;

    public ChaptersViewModel(@NonNull Application application) {
        super(application);
        chaptersRepository = new ChaptersRepository(application);
        recentChapters = chaptersRepository.getRecentChapters();
        allChapters = chaptersRepository.getAllChapters();

    }

    public LiveData<List<Chapter>> getRecentChapters() {
        return recentChapters;
    }
    public LiveData<List<Chapter>> getAllChapters() {
        return allChapters;
    }
    public CompositeDisposable getDisposables() {
        return chaptersRepository.recentChaptersCompositeDisposable;
    }

}

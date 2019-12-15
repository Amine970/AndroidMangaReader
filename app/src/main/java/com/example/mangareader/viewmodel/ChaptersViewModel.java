package com.example.mangareader.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mangareader.model.Chapter;
import com.example.mangareader.model.repository.ChaptersRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class ChaptersViewModel extends AndroidViewModel {

    private LiveData<List<Chapter>> recentChapters;
    private LiveData<List<Chapter>> allChapters;
    private ChaptersRepository chaptersRepository;
    private LiveData<List<Chapter>> chaptersByMangaID;
    private Observable<LiveData<List<Chapter>>> chaptersByMangaIDObservable;
    private Observable<LiveData<List<Chapter>>> recentChaptersObservable;
    public ChaptersViewModel(@NonNull Application application, String mangaID) {
        super(application);
        chaptersRepository = new ChaptersRepository(application, mangaID);
        recentChapters = chaptersRepository.getRecentChapters();
        //allChapters = chaptersRepository.getAllChapters();
        //chaptersByMangaID = chaptersRepository.getChaptersByMangaID();
        chaptersByMangaIDObservable = chaptersRepository.getChaptersByMangaIDObservable();
        recentChaptersObservable = chaptersRepository.getRecentChaptersObservable();
    }

    public LiveData<List<Chapter>> getRecentChapters() {
        return recentChapters;
    }
    //public LiveData<List<Chapter>> getAllChapters() {
        //return allChapters;
    //}

    public LiveData<List<Chapter>> getGetChaptersByMangaID() {
        return chaptersByMangaID;
    }

    public Observable<LiveData<List<Chapter>>> getChaptersByMangaIDObservable() {
        return chaptersByMangaIDObservable;
    }

    public Observable<LiveData<List<Chapter>>> getRecentChaptersObservable() {
        return recentChaptersObservable;
    }

    public CompositeDisposable getDisposables() {
        return chaptersRepository.recentChaptersCompositeDisposable;
    }

}

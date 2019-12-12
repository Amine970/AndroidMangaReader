package com.example.mangareader.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mangareader.model.Manga;
import com.example.mangareader.model.repository.MangaRepository;

import java.util.List;

public class MangasDetailsViewModel extends AndroidViewModel {
    private MangaRepository mangaRepository;
    private Manga manga;
    private LiveData<Manga> mangaById;
    public MangasDetailsViewModel(@NonNull Application application, Manga manga) {
        super(application);
        this.manga = manga;
        mangaRepository = new MangaRepository(application, manga);
        //mangaById = mangaRepository.getMangaById();
    }
    public LiveData<Manga> getMangaById() {
        return mangaRepository.getMangaById();
    }
}

package com.example.mangareader.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mangareader.model.Manga;
import com.example.mangareader.model.repository.MangaRepository;

import java.util.List;

public class MangaViewModel extends AndroidViewModel {
    private MangaRepository mangaRepository;
    private LiveData<List<Manga>> allMangas;
    public MangaViewModel(@NonNull Application application) {
        super(application);
        mangaRepository = new MangaRepository(application);
        allMangas = mangaRepository.getAllMangas();
    }

    public LiveData<List<Manga>> getAllMangas() {
        return allMangas;
    }

}

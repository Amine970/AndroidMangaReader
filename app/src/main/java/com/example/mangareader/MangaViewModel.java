package com.example.mangareader;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

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

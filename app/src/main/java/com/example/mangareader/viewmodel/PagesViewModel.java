package com.example.mangareader.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mangareader.model.data.Page;
import com.example.mangareader.model.repository.PagesRepository;

import java.util.List;

public class PagesViewModel extends AndroidViewModel {
    private PagesRepository pagesRepository;
    private LiveData<List<Page>> pages;
    public PagesViewModel(@NonNull Application application, String chapterID) {
        super(application);
        pagesRepository = new PagesRepository(application, chapterID);
        pages = pagesRepository.getPages();
    }

    public LiveData<List<Page>> getPages() {
        return pages;
    }
}

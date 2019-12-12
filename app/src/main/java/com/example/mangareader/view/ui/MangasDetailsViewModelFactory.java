package com.example.mangareader.view.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mangareader.model.Manga;
import com.example.mangareader.viewmodel.MangasDetailsViewModel;

public class MangasDetailsViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private Manga manga;

    public MangasDetailsViewModelFactory(Application application, Manga manga) {
        this.application = application;
        this.manga = manga;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MangasDetailsViewModel(application, manga);
    }
}

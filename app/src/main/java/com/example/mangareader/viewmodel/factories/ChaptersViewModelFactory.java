package com.example.mangareader.viewmodel.factories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mangareader.viewmodel.ChaptersViewModel;

public class ChaptersViewModelFactory implements ViewModelProvider.Factory{

    private Application application;
    private String mangaID;

    public ChaptersViewModelFactory(Application application, String mangaID) {
        this.application = application;
        this.mangaID = mangaID;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ChaptersViewModel(application, mangaID);
    }
}

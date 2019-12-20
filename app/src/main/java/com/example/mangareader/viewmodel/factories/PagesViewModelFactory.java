package com.example.mangareader.viewmodel.factories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mangareader.viewmodel.PagesViewModel;

public class PagesViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private String chapterID;

    public PagesViewModelFactory(Application application, String chapterID) {
        this.application = application;
        this.chapterID = chapterID;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PagesViewModel(application, chapterID);
    }
}

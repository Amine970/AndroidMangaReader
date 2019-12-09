package com.example.mangareader.model.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mangareader.model.Manga;
import com.example.mangareader.model.MangaDao;
import com.example.mangareader.model.MangaRoomDatabase;
import com.example.mangareader.model.AllMangas;
import com.example.mangareader.model.remote.RetrofitClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MangaRepository {
    private static final String TAG = "debugging";
    private MangaDao mangaDao;
    private LiveData<List<Manga>> allMangas;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MangaRepository(Application application) {
        MangaRoomDatabase db = MangaRoomDatabase.getDatabase(application);
        mangaDao = db.mangaDao();
        allMangas = mangaDao.getAllMangas();
        compositeDisposable.add(getMangasObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<Manga>() {
                    @Override
                    public void onNext(Manga manga) {
                        mangaDao.insert(manga);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e + "\n" + e.getCause() + "\n" + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
                    }
                    @Override
                    public void onComplete() {
                    }
                }));
    }

    public LiveData<List<Manga>> getAllMangas() {
        return allMangas;
    }

    private Observable<Manga> getMangasObservable () {
        return RetrofitClass.getApiService()
                .getMangas()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(AllMangas::getManga)
                .flatMap((Function<List<Manga>, ObservableSource<Manga>>) mangas -> {
                    //Log.i(TAG, "apply: " + mangas.get(0).getTitle() + " id -> " + mangas.get(0).getTitle());
                    Collections.sort(mangas);
                    List<Manga> reducedList = new ArrayList<>();
                    for(int i = 0; i < Math.min(300, mangas.size()); i++)
                        reducedList.add(mangas.get(i));
                    return Observable.fromIterable(reducedList.stream().filter(manga -> manga.getId() != null && manga.getImage() != null).collect(Collectors.toList()))
                            .subscribeOn(Schedulers.io());
                })
                .sorted();
    }
}

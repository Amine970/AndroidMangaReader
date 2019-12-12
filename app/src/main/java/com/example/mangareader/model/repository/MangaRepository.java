package com.example.mangareader.model.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mangareader.model.Chapter;
import com.example.mangareader.model.ChapterDao;
import com.example.mangareader.model.Manga;
import com.example.mangareader.model.MangaDao;
import com.example.mangareader.model.MangaRoomDatabase;
import com.example.mangareader.model.AllMangas;
import com.example.mangareader.model.remote.RetrofitClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MangaRepository {
    private static final String TAG = "debugging";
    private MangaDao mangaDao;
    private ChapterDao chapterDao;
    private LiveData<List<Manga>> allMangas;
    private LiveData<Manga> mangaById;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MangaRepository(Application application, Manga manga) {
        MangaRoomDatabase db = MangaRoomDatabase.getDatabase(application);
        mangaDao = db.mangaDao();
        chapterDao = db.chapterDao();
        allMangas = mangaDao.getAllMangas();
        if(manga != null) {
            mangaById = mangaDao.getMangaById(manga.getId());
        }
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

        if(manga != null) {
            getDetailsObservable(manga)
                .subscribe(new Consumer<Manga>() {
                    @Override
                    public void accept(Manga manga) throws Exception {
                        Log.i(TAG, "accept: author -> " + manga.getAuthor());
                    }
                });
        }
    }

    public LiveData<Manga> getMangaById() {
        return mangaById;
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

    private Observable<Manga> getDetailsObservable(final Manga manga) {
        return RetrofitClass.getApiService()
                .getMangaDetails(manga.getId())
                .map(new Function<Manga, Manga>() {
                    @Override
                    public Manga apply(Manga mangaDetails) throws Exception {
                        //Log.i(TAG, "applyDetails: ici avec " + mangaDetails.getAuthor());
                        manga.setAuthor(mangaDetails.getAuthor());
                        manga.setMangaChaptersFromStringsList(mangaDetails.getChapters());
                        manga.setDescription(mangaDetails.getDescription());
                        manga.setReleased(mangaDetails.getReleased());
                        mangaDao.updateChapter(mangaDetails.getAuthor(), mangaDetails.getDescription(), mangaDetails.getReleased(), manga.getId());
                        //Log.i(TAG, "apply: nbchapter de ce manga -> " + manga.getMangaChapters().size());
                        for (Chapter chapter : manga.getMangaChapters()) {
                            chapter.setMangaTitle(manga.getTitle());
                            chapter.setHits(manga.getHits());
                            chapterDao.insert(chapter);
                        }

                        //chapterDao.insert(manga.getMangaChapters().get(0));
                        //Log.i(TAG, "apply: nb chapters inserted -> " + chapterDao.getNumberOfChapters());
                        return manga;
                    }
                })
                .subscribeOn(Schedulers.io());
    }
    /*public Manga getMangaByID(String ID) {
        Observable<List<Manga>> mangaByIdobservable = Observable
                .fromCallable(() -> mangaDao.getMangaById(ID))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        List<Manga> mangas = new ArrayList<>();
        mangaByIdobservable.subscribe(mangass -> mangas.add(mangass.get(0)));
        Log.i(TAG, "getMangaByID:  -> " + mangas.get(0).getTitle());
        return mangas.get(0);
    }*/
}

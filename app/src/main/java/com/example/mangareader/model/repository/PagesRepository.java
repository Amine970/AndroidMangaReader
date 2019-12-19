package com.example.mangareader.model.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mangareader.model.data.Chapter;
import com.example.mangareader.model.data.ChapterDao;
import com.example.mangareader.model.data.ChapterDetails;
import com.example.mangareader.model.data.Manga;
import com.example.mangareader.model.data.MangaDao;
import com.example.mangareader.model.data.MangaRoomDatabase;
import com.example.mangareader.model.data.Page;
import com.example.mangareader.model.data.PageDao;
import com.example.mangareader.model.remote.RetrofitClass;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PagesRepository {
    private static final String TAG = "debugging";
    private ChapterDao chapterDao;
    private PageDao pageDao;
    private String chapterID;
    private LiveData<List<Page>> pages;
    public PagesRepository(Application application, String chapterID) {
        MangaRoomDatabase db = MangaRoomDatabase.getDatabase(application);
        this.chapterID = chapterID;
        chapterDao = db.chapterDao();
        pageDao = db.pageDao();
        pages = pageDao.getPagesByChapterID(chapterID);
        getChapterDetails(chapterID)
                .subscribe(new Observer<Page>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Page page) {
                        pageDao.insert(page);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e.getMessage() + "\n" + e.getCause());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public LiveData<List<Page>> getPages() {
        return pages;
    }

    public static Observable<Page> getChapterDetails(String chapterID) {
        return RetrofitClass
                .getApiService()
                .getChapterDetails(chapterID)
                .flatMap(new Function<ChapterDetails, ObservableSource<Page>>() {
                    @Override
                    public ObservableSource<Page> apply(ChapterDetails chapterDetails) throws Exception {
                        List<Page> pages = new ArrayList<>();
                        for(List<String> pageStrings : chapterDetails.getRawPages()) {
                            if(pageStrings.size() >= 4) {
                                if(!pageStrings.get(0).contains(".")){
                                    Page page = new Page(Integer.parseInt(pageStrings.get(0)), pageStrings.get(1), chapterID);
                                    pages.add(page);
                                }
                            }

                        }
                        Observable<Page> pageObservable = Observable.fromIterable(pages)
                                .sorted()
                                .subscribeOn(Schedulers.io()).observeOn(Schedulers.io());
                        return pageObservable;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
}

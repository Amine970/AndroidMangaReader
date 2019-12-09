package com.example.mangareader.model.repository;

import android.app.Application;
import android.telecom.Call;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.example.mangareader.model.Chapter;
import com.example.mangareader.model.ChapterDao;
import com.example.mangareader.model.Manga;
import com.example.mangareader.model.MangaDao;
import com.example.mangareader.model.MangaRoomDatabase;
import com.example.mangareader.model.remote.RetrofitClass;
import com.example.mangareader.view.ui.MainActivity;
import com.example.mangareader.view.ui.RecentChaptersFragment;

import org.reactivestreams.Publisher;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ChaptersRepository {
    private static final String TAG = "debugging";

    private ChapterDao chapterDao;
    private MangaDao mangaDao;
    private LiveData<List<Chapter>> allChapters;
    private LiveData<List<Chapter>> recentChapters;
    //private LiveData<List<Manga>> mangasWithRecentChapter;
    public ChaptersRepository(Application application) {
        MangaRoomDatabase db = MangaRoomDatabase.getDatabase(application);
        chapterDao = db.chapterDao();
        mangaDao = db.mangaDao();
        //mangasWithRecentChapter = mangaDao.getMangasWithRecentChapter();
        allChapters = chapterDao.getAllChapters();
        Long tsLong = System.currentTimeMillis()/1000;
        recentChapters = chapterDao.getRecentChapters(tsLong - 2*604800);
        //recentChapters = chapterDao.getRecentChapters(tsLong - 2*604800);//tsLong - 2*604800);   //nb Secondes en 1 semaine = 7*24*60*60 = 604800
        //new insertAsyncTask(chapterDao, mangaDao).execute();
        Observable<Manga> mangasWithRecentChapter = Observable
                .fromCallable(() -> mangaDao.getMangasWithRecentChapter())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())    //AndroidSchedulers.mainThread()
                .flatMap(new Function<List<Manga>, ObservableSource<Manga>>() {
                    @Override
                    public ObservableSource<Manga> apply(List<Manga> mangas) throws Exception {
                        return Observable.fromIterable(mangas).subscribeOn(Schedulers.io());
                    }
                });
        mangasWithRecentChapter.subscribe(new Observer<Manga>() {
            @Override
            public void onSubscribe(Disposable d) {
            }
            @Override
            public void onNext(Manga myManga) {
                getDetailsObservable(myManga).subscribe(new Observer<Manga>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(Manga manga) {
                        Log.i(TAG, "onNext: " + myManga.getTitle());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e + "\n" + e.getCause() + "\n" + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public LiveData<List<Chapter>> getAllChapters() {
        return allChapters;
    }

    public LiveData<List<Chapter>> getRecentChapters() {
        return recentChapters;
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
                        /*
                         @Query("UPDATE mangas_table SET author = :author, description = :description, released = :released")
    void updateChapter(String author, String description, int released);
                         */
                        mangaDao.updateChapter(mangaDetails.getAuthor(), mangaDetails.getDescription(), mangaDetails.getReleased());
                        /*Observable.fromIterable(manga.getMangaChapters())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(chapter -> chapterDao.insert(chapter));*/
                        //Thread.sleep(2000);
                        Log.i(TAG, "apply: nbchapter de ce manga -> " + manga.getMangaChapters().size());
                        for(Chapter chapter : manga.getMangaChapters()) {
                            chapter.setMangaTitle(manga.getTitle());
                            chapter.setHits(manga.getHits());
                            chapterDao.insert(chapter);
                        }

                        //chapterDao.insert(manga.getMangaChapters().get(0));
                        Log.i(TAG, "apply: nb chapters inserted -> " + chapterDao.getNumberOfChapters() );
                        return manga;
                    }
                })
                .subscribeOn(Schedulers.io());
    }

}

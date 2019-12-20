package com.example.mangareader.model.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mangareader.model.data.Chapter;
import com.example.mangareader.model.data.ChapterDao;
import com.example.mangareader.model.data.Manga;
import com.example.mangareader.model.data.MangaDao;
import com.example.mangareader.model.data.MangaRoomDatabase;
import com.example.mangareader.model.remote.RetrofitClass;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ChaptersRepository {
    private static final String TAG = "debugging";

    private  ChapterDao chapterDao;
    private  MangaDao mangaDao;
    private LiveData<List<Chapter>> allChapters;
    private LiveData<List<Chapter>> recentChapters;
    private LiveData<List<Chapter>> chaptersByMangaID;
    private String mangaID;
    public CompositeDisposable recentChaptersCompositeDisposable;
    private static List<Chapter> chaptersListTmp;
    public ChaptersRepository(Application application, String mangaID) {
        //Log.i(TAG, "constructor chapters repository: Am I on Main Thread ? -> " + (Looper.myLooper() == Looper.getMainLooper()));
        this.mangaID = mangaID;
        MangaRoomDatabase db = MangaRoomDatabase.getDatabase(application);
        recentChaptersCompositeDisposable = new CompositeDisposable();
        chapterDao = db.chapterDao();
        mangaDao = db.mangaDao();
        chaptersByMangaID = chapterDao.getChaptersByMangaID(mangaID);
        long tsLong = System.currentTimeMillis()/1000;
        recentChapters = chapterDao.getRecentChapters(tsLong - 2*604800);
        if(mangaID == null) {
            getMangasWithRecentChapter()
                    .observeOn(Schedulers.io())
                    .subscribe(new Observer<Manga>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            //Log.i(TAG, "ChaptersRepository: dans onsubscribe");
                            recentChaptersCompositeDisposable.add(d);
                        }
                        @Override
                        public void onNext(Manga manga) {
                            //Log.i(TAG, "onNext: inserting chapter for recents");
                            getDetailsObservable(manga, chapterDao, mangaDao)
                                    .subscribe((x) -> {}, error -> {});
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }

    }
    public LiveData<List<Chapter>> getRecentChapters() {
        return recentChapters;
    }

    public LiveData<List<Chapter>> getChaptersByMangaID() {
        return chaptersByMangaID;
    }

    public Observable<Manga> getMangasWithRecentChapter() {
        return Observable
                .fromCallable(() -> mangaDao.getMangasWithRecentChapter())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())    //AndroidSchedulers.mainThread()
                .flatMap(new Function<List<Manga>, ObservableSource<Manga>>() {
                    @Override
                    public ObservableSource<Manga> apply(List<Manga> mangas) throws Exception {
                        //Log.i(TAG, "apply: apply flatmap liste mangas à");
                        return Observable.fromIterable(mangas).subscribeOn(Schedulers.io());
                    }
                });
    }
    public static Observable<Manga> getDetailsObservable(final Manga manga, ChapterDao chapterDao, MangaDao mangaDao) {
        return RetrofitClass.getApiService()
                .getMangaDetails(manga.getId())
                .map(new Function<Manga, Manga>() {
                    @Override
                    public Manga apply(Manga mangaDetails) throws Exception {
                        //Log.i(TAG, "map apply: Am I on Main Thread ? -> " + (Looper.myLooper() == Looper.getMainLooper()));
                        Log.i(TAG, "applyDetails: ici avec " + mangaDetails.getAuthor());
                        manga.setAuthor(mangaDetails.getAuthor());
                        manga.setMangaChaptersFromStringsList(mangaDetails.getChapters());
                        manga.setDescription(mangaDetails.getDescription());
                        manga.setReleased(mangaDetails.getReleased());
                        mangaDao.updateChapter(mangaDetails.getAuthor(), mangaDetails.getDescription(), mangaDetails.getReleased(), manga.getId());
                        for(int i = 0; i < Math.min(manga.getMangaChapters().size(), 75); i++) {
                            Chapter chapter = manga.getMangaChapters().get(i);
                            if(!chapter.getNumber().contains(".")) {    // && i < 500
                                chapter.setMangaTitle(manga.getTitle());
                                chapter.setHits(manga.getHits());
                                chapter.setCategory(manga.getCategory());
                                //chaptersListTmp.add(chapter);//////////////////////////////////////////////
                                chapterDao.insert(chapter);
                            }
                        }
                        return manga;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }


}

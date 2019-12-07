package com.example.mangareader.model.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mangareader.model.Chapter;
import com.example.mangareader.model.ChapterDao;
import com.example.mangareader.model.Manga;
import com.example.mangareader.model.MangaDao;
import com.example.mangareader.model.MangaDetails;
import com.example.mangareader.model.MangaRoomDatabase;
import com.example.mangareader.model.AllMangas;
import com.example.mangareader.model.remote.MangasApi;
import com.example.mangareader.model.remote.RetrofitClass;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MangaRepository {
    private static final String TAG = "debugging";
    private MangaDao mangaDao;
    private ChapterDao chapterDao;
    private LiveData<List<Manga>> allMangas;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MangaRepository(Application application) {
        MangaRoomDatabase db = MangaRoomDatabase.getDatabase(application);
        mangaDao = db.mangaDao();
        chapterDao = db.chapterDao();
        allMangas = mangaDao.getAllMangas();
        compositeDisposable.add(getMangasObservable()
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Manga, ObservableSource<Manga>>() {
                    @Override
                    public ObservableSource<Manga> apply(Manga manga) throws Exception {
                        //return null; // return an updated Observable<Manga> with chapters
                        Thread.sleep(1500);
                        return getDetailsObservable(manga);
                    }
                })
                .observeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<Manga>() {
                    @Override
                    public void onNext(Manga manga) {
                        //update the manga in the list
                        Log.i(TAG, "onNext: mangaId -> " + manga.getId());// + "nbChaps -> " + manga.getMangaChapters().size());
                        mangaDao.insert(manga);
                        Log.i(TAG, "onNext: nbChapters du manga " + manga.getTitle() + " -> " + manga.getMangaChapters().size());
                        for(Chapter chapter : manga.getMangaChapters()) {
                            chapter.setMangaTitle(manga.getTitle());
                            chapterDao.insert(chapter);
                            //Log.i(TAG, "onNext: chapters inserted jusque là -> " + chapterDao.getNumberOfChapters());
                        }
                        //mangaDao.insert(manga);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e + "\n" + e.getCause() + "\n" + e.getMessage() + "\n" + e.getStackTrace());

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    public LiveData<List<Manga>> getAllMangas() {
        return allMangas;
    }

    public void insert (AllMangas AllMangass) {
        new insertAsyncTask(mangaDao).execute(AllMangass);
    }

    private Observable<Manga> getMangasObservable () {
        return RetrofitClass.getApiService().getMangas()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(AllMangas::getManga)
                .flatMap((Function<List<Manga>, ObservableSource<Manga>>) mangas -> {
                    //Log.i(TAG, "apply: " + mangas.get(0).getTitle() + " id -> " + mangas.get(0).getTitle());
                    return Observable.fromIterable(mangas.stream().filter(manga -> manga.getId() != null && manga.getImage() != null).collect(Collectors.toList()))
                            .subscribeOn(Schedulers.io());
                });
    }
    private Observable<Manga> getDetailsObservable(final Manga manga) {
        return RetrofitClass.getChaptersApiService()
                .getMangaDetails(manga.getId())
                .map(new Function<Manga, Manga>() {
                    @Override
                    public Manga apply(Manga mangaDetails) throws Exception {
                        //Log.i(TAG, "applyDetails: ici avec " + mangaDetails.getAuthor());
                        manga.setAuthor(mangaDetails.getAuthor());
                        manga.setMangaChapters1(mangaDetails.getChapters());
                        manga.setDescription(mangaDetails.getDescription());
                        manga.setReleased(mangaDetails.getReleased());
                        return manga;
                    }
                })
                .subscribeOn(Schedulers.io());
    }
    /*
        manga.setAuthor(mangaDetails.getAuthor());
        manga.setMangaChapters1(mangaDetails.getChapters());
        manga.setDescription(mangaDetails.getDescription());
        manga.setReleased(mangaDetails.getReleased());
     */
    private static class insertAsyncTask extends AsyncTask<AllMangas, Void, Void> {
        private MangaDao asyncMangaDao;
        public insertAsyncTask(MangaDao mangaDao) {
            this.asyncMangaDao = mangaDao;
        }

        @Override
        protected final Void doInBackground(AllMangas... AllMangass) { // 7021 mangas à la base
            int nbRows = asyncMangaDao.getNumberOfRows();
            Log.i(TAG, "doInBackground: nbRows : " + nbRows);
            if(nbRows < 500) { // check si y a déjà la data dans la base
                List<Manga> filteredMangas;// = Arrays.asList(AllMangass[0].getManga());
                filteredMangas = AllMangass[0].getManga().stream().filter(manga -> manga.getId() != null && manga.getImage() != null).collect(Collectors.toList());
                Collections.sort(filteredMangas);
                for (int i = 0; i < filteredMangas.size() / 10; i++) {
                    asyncMangaDao.insert(filteredMangas.get(i));
                }
            } else Log.i(TAG, "doInBackground: data already present");
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}

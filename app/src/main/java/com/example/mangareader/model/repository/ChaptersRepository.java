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
import com.example.mangareader.model.remote.ChaptersApi;
import com.example.mangareader.model.remote.RetrofitClass;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChaptersRepository {
    private ChapterDao chapterDao;
    private MangaDao mangaDao;
    private LiveData<List<Chapter>> allChapters;
    private LiveData<List<Chapter>> recentChapters;
    public ChaptersRepository(Application application) {
        MangaRoomDatabase db = MangaRoomDatabase.getDatabase(application);
        chapterDao = db.chapterDao();
        mangaDao = db.mangaDao();
        allChapters = chapterDao.getAllChapters();
        Long tsLong = System.currentTimeMillis()/1000;
        recentChapters = chapterDao.getRecentChapters(tsLong - 2*604800);//tsLong - 2*604800);   //nb Secondes en 1 semaine = 7*24*60*60 = 604800
        new insertAsyncTask(chapterDao, mangaDao).execute();
    }

    public LiveData<List<Chapter>> getAllChapters() {
        return allChapters;
    }

    public LiveData<List<Chapter>> getRecentChapters() {
        return recentChapters;
    }

    public static class insertAsyncTask extends AsyncTask<Void, Void, Void> {

        private ChapterDao chapterDao;
        private MangaDao mangaDao;
        private List<String> allMangasIds;
        private static final String TAG = "debugging";

        public insertAsyncTask(ChapterDao chapterDao, MangaDao mangaDao) {
            this.chapterDao = chapterDao;
            this.mangaDao = mangaDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ChaptersApi chaptersApi = RetrofitClass.getChaptersApiService();
            allMangasIds = mangaDao.getAllMangasIds();
            for(String mangaId : allMangasIds) {
                //Log.i(TAG, "onResponse: manga id -> " + mangaId);
                Log.i(TAG, "onResponse: un manga done, nbChapters inserted  ->  " + chapterDao.getNumberOfChapters());

                Call<MangaDetails> call = chaptersApi.getMangaDetails(mangaId);
                call.enqueue(new Callback<MangaDetails>() {
                    @Override
                    public void onResponse(Call<MangaDetails> call, Response<MangaDetails> response) {
                        MangaDetails details = response.body();
                        if(details != null) {
                            Log.i(TAG, "onResponse: details non null");
                            for(List<String> chap : details.getChapters()) {
                                Log.i(TAG, "onResponse: non nul du coup dans for");
                                int nonDigitsChapDate = chap.get(1).replaceAll("[^0-9]", "").length();
                                int nonDigitsChapNum = chap.get(0).replaceAll("[^0-9]", "").length();
                                if(nonDigitsChapDate == 0 && nonDigitsChapNum == 0) {
                                    Log.i(TAG, "onResponse: manga id -> " + mangaId +" chapNum -> " + chap.get(0) + " chapDate-> " + chap.get(1) + " chapTitle-> " + chap.get(2) + "\n");
                                    Chapter chapter = new Chapter(Integer.parseInt(chap.get(0)),Long.parseLong(chap.get(1)), chap.get(2),chap.get(3), mangaId);
                                    chapterDao.insert(chapter);
                                }
                                //else
                                    //Log.i(TAG, "onResponse: erreur non entier");
                            }
                        }
                        //else Log.i(TAG, "onResponse: response null lol");
                    }
                    @Override
                    public void onFailure(Call<MangaDetails> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage() + "\n" + t.getCause());
                    }
                });

            }

            return null;
        }
    }
}

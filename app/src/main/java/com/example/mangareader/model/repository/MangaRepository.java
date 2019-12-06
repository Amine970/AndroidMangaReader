package com.example.mangareader.model.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mangareader.model.Manga;
import com.example.mangareader.model.MangaDao;
import com.example.mangareader.model.MangaRoomDatabase;
import com.example.mangareader.model.AllMangas;
import com.example.mangareader.model.remote.MangasApi;
import com.example.mangareader.model.remote.RetrofitClass;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MangaRepository {
    private static final String TAG = "debugging";
    private MangaDao mangaDao;
    private LiveData<List<Manga>> allMangas;
    public MangaRepository(Application application) {
        MangaRoomDatabase db = MangaRoomDatabase.getDatabase(application);
        mangaDao = db.mangaDao();
        allMangas = mangaDao.getAllMangas();
        MangasApi mangasApi = RetrofitClass.getApiService();
        Call<AllMangas> call = mangasApi.getMangas();
        call.enqueue(new Callback<AllMangas>() {
            @Override
            public void onResponse(Call<AllMangas> call, Response<AllMangas> response) {
                if(response.body() != null)
                    insert(response.body());
                else
                    Log.d(TAG, "onResponse: null" + response);
            }
            @Override
            public void onFailure(Call<AllMangas> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage() + "\n" + t.getCause());
            }
        });
    }

    public LiveData<List<Manga>> getAllMangas() {
        return allMangas;
    }

    public void insert (AllMangas AllMangass) {
        new insertAsyncTask(mangaDao).execute(AllMangass);
    }
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

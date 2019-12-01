package com.example.mangareader;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mangareader.remote.MangaResponse;
import com.example.mangareader.remote.MangasApi;
import com.example.mangareader.remote.RetrofitClass;

import java.util.ArrayList;
import java.util.List;

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
        final List<Manga> mangasList = new ArrayList<>();
        MangasApi mangasApi = RetrofitClass.getApiService();
        Call<MangaResponse> call = mangasApi.getMangas();
        call.enqueue(new Callback<MangaResponse>() {
            @Override
            public void onResponse(Call<MangaResponse> call, Response<MangaResponse> response) {
                if(response.body() != null) {
                    for(Manga manga : response.body().getManga()){
                        mangasList.add(manga);
                        //Log.i(TAG, "onResponse: " + manga.getTitle());
                    }
                    insert(mangasList);
                }
                else
                    Log.d(TAG, "onResponse: null" + response);
            }

            @Override
            public void onFailure(Call<MangaResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage() + "\n" + t.getCause());
            }
        });
    }

    public LiveData<List<Manga>> getAllMangas() {
        return allMangas;
    }

    public void insert (List<Manga> mangas) {
        new insertAsyncTask(mangaDao).execute(mangas);
    }
    private static class insertAsyncTask extends AsyncTask<List<Manga>, Void, Void> {
        private MangaDao asyncMangaDao;
        public insertAsyncTask(MangaDao mangaDao) {
            this.asyncMangaDao = mangaDao;
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(List<Manga>... mangas) {
            for(Manga manga : mangas[0])
                asyncMangaDao.insert(manga);
            return null;
        }
    }
}

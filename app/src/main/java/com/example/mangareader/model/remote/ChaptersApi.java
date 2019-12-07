package com.example.mangareader.model.remote;

import com.example.mangareader.model.MangaDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ChaptersApi {
    @GET("manga/{mangaID}")
    Call<MangaDetails> getMangaDetails(@Path("mangaID") String mangaID);
}

package com.example.mangareader.remote;

import com.example.mangareader.Manga;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MangasApi {
    @GET("list/0")
    Call<MangaResponse> getMangas();
}

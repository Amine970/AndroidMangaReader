package com.example.mangareader.model.remote;



import com.example.mangareader.model.AllMangas;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MangasApi {
    @GET("list/0")
    Call<AllMangas> getMangas();
}

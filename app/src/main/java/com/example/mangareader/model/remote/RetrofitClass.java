package com.example.mangareader.model.remote;

import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitClass
{
    private static final String BASE_URL = "https://www.mangaeden.com/api/";
    private static Retrofit getRetroInstance()
    {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static MangasApi getApiService()
    {
        return getRetroInstance().create(MangasApi.class);
    }
    public static ChaptersApi getChaptersApiService() {
        return getRetroInstance().create(ChaptersApi.class);
    }
}
package com.example.mangareader.model.remote;




import com.example.mangareader.model.AllMangas;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MangasApi {
    @GET("list/0")
    Observable<AllMangas> getMangas();
}

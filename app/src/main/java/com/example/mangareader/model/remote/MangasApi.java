package com.example.mangareader.model.remote;




import com.example.mangareader.model.data.AllMangas;
import com.example.mangareader.model.data.ChapterDetails;
import com.example.mangareader.model.data.Manga;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MangasApi {
    @GET("list/0")
    Observable<AllMangas> getMangas();

    @GET("manga/{mangaID}")
    Observable<Manga> getMangaDetails(@Path("mangaID") String mangaID);

    @GET("chapter/{chapterID}")
    Observable<ChapterDetails> getChapterDetails(@Path("chapterID") String chapterID);
}

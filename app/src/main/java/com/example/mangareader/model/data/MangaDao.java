package com.example.mangareader.model.data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MangaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Manga manga);

    @Query("SELECT * from mangas_table ORDER BY hits DESC")     //title ASC     //hits DESC
    LiveData<List<Manga>> getAllMangas();

    @Query("SELECT * from mangas_table ORDER BY lastChapterDate DESC LIMIT 20") //   //SELECT * from mangas_table ORDER BY lastChapterDate DESC LIMIT 10 // (SELECT * from mangas_table ORDER BY hits DESC LIMIT 50)
    List<Manga> getMangasWithRecentChapter();

    @Query("SELECT id from mangas_table")
    List<String> getAllMangasIds();

    @Query("SELECT COUNT(*) FROM mangas_table")
    int getNumberOfRows();

    @Query("UPDATE mangas_table SET author = :author, description = :description, released = :released WHERE id = :id")
    void updateChapter(String author, String description, int released, String id);

    @Query("SELECT * FROM mangas_table WHERE id = :id")
    LiveData<Manga> getMangaById(String id);
}
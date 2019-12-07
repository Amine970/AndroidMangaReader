package com.example.mangareader.model;

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

    @Query("SELECT * from mangas_table ORDER BY hits DESC")
    LiveData<List<Manga>> getAllMangas();

    @Query("SELECT id from mangas_table")
    List<String> getAllMangasIds();

    @Query("SELECT COUNT(*) FROM mangas_table")
    int getNumberOfRows();
}
//@Query("SELECT * from (SELECT * from mangas_table ORDER BY lastChapterDate DESC LIMIT 20) ORDER BY hits DESC")
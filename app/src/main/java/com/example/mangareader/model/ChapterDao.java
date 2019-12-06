package com.example.mangareader.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChapterDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Chapter chapter);

    @Query("SELECT * from chapters_table ORDER BY date DESC")
    List<Chapter> getAllChapters();

    @Query("SELECT COUNT(*) FROM mangas_table")
    int getNumberOfRows();
}

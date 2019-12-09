package com.example.mangareader.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChapterDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)   //
    void insert(Chapter chapter);

    @Query("SELECT * from chapters_table")
    LiveData<List<Chapter>> getAllChapters();

    @Query("SELECT * from chapters_table WHERE date > :time ORDER BY hits DESC")   // linux timestamp,  WHERE date > :time
    LiveData<List<Chapter>> getRecentChapters(Long time);    //Long time

    @Query("SELECT COUNT(*) FROM chapters_table")
    int getNumberOfChapters();
}

package com.example.mangareader.model.data;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.PagedList;
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

    @Query("SELECT * from chapters_table WHERE mangaId = :mangaID ")
    LiveData<List<Chapter>> getChaptersByMangaID(String mangaID);

    @Query("SELECT * from chapters_table WHERE date > :time ORDER BY date DESC")   // linux timestamp,  WHERE date > :time
    LiveData<List<Chapter>> getRecentChapters(Long time);    //Long time

    @Query("SELECT COUNT(*) FROM chapters_table")
    int getNumberOfChapters();

    @Query("DELETE FROM chapters_table")
    void deleteAll();
}

package com.example.mangareader.model.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Page page);

    @Query("SELECT * FROM pages_table WHERE chapterID = :chapterID")
    LiveData<List<Page>> getPagesByChapterID(String chapterID);
}

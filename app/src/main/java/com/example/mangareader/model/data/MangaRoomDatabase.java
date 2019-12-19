package com.example.mangareader.model.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.mangareader.Converters;

@Database(entities = {Manga.class, Chapter.class, Page.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class MangaRoomDatabase extends RoomDatabase {
    private static final String TAG = "debugging";
    private static MangaRoomDatabase INSTANCE;
    public abstract MangaDao mangaDao();
    public abstract ChapterDao chapterDao();
    public abstract PageDao pageDao();
    public static MangaRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (MangaRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MangaRoomDatabase.class, "manga_database")
                            .fallbackToDestructiveMigration()
                            //.addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

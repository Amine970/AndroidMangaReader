package com.example.mangareader.model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mangareader.Converters;

@Database(entities = {Manga.class, Chapter.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class MangaRoomDatabase extends RoomDatabase {
    private static final String TAG = "debugging";
    private static MangaRoomDatabase INSTANCE;
    public abstract MangaDao mangaDao();
    public abstract ChapterDao chapterDao();

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

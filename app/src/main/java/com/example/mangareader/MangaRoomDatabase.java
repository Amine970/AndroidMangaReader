package com.example.mangareader;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Manga.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class MangaRoomDatabase extends RoomDatabase {
    private static final String TAG = "debugging";
    private static MangaRoomDatabase INSTANCE;
    public abstract MangaDao mangaDao();

    public static MangaRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (MangaRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MangaRoomDatabase.class, "manga_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

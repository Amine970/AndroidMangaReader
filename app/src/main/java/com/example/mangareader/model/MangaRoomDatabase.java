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
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ChapterDao mDao;

        PopulateDbAsync(MangaRoomDatabase db) {
            mDao = db.chapterDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            //mDao.deleteAll();

            for (int i = 0; i < 10; i++) {
                Chapter chapter  = new Chapter(i, 123, "titre"+i, "id"+i, "mangaId");
                mDao.insert(chapter);
            }
            return null;
        }
    }
}

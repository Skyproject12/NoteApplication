package com.example.noteapplication.Data.Source.Locale;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.noteapplication.Data.Module.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@Database(entities = {Note.class}, version = 1)
public abstract class roomDatabase extends RoomDatabase {

    // initial interface dao
    public abstract NoteDao noteDao();

    // make instance
    private static volatile roomDatabase INSTANCE;

    // initial database
    public static roomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (roomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            roomDatabase.class, "note_database").addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            add();
                        }
                    }).build();

                }
            }
        }
        return INSTANCE;
    }

    // add dummy data
    private static void add() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                final List<Note> list = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    list.add(new Note("Tugas " + i, "Belajar Modul " + i, "2019/09/09 09:09:0" + i));
                }
                INSTANCE.noteDao().insertData(list);
            }
        });
    }

}

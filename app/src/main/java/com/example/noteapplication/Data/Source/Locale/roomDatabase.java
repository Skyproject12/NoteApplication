package com.example.noteapplication.Data.Source.Locale;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.noteapplication.Data.Module.Note;

@Database(entities= {Note.class}, version = 1)
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
                            roomDatabase.class, "note_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}

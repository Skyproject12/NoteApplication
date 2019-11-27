package com.example.noteapplication.Data.Source.Locale;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.noteapplication.Data.Module.Note;

import java.util.List;

public interface NoteDao {
    @Insert(onConflict= OnConflictStrategy.IGNORE)
    void insert(Note note);

    // make function update
    @Update
    void update (Note note);

    // delete function query
    @Delete()
    void delete(Note note);

    // select id
    @Query("SELECT * from note ORDER BY id ASC")
    LiveData<List<Note>> getAllNotes();

}

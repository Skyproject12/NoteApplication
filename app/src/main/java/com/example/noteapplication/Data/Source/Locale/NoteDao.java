package com.example.noteapplication.Data.Source.Locale;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.example.noteapplication.Data.Module.Note;

import java.util.List;


@Dao
public interface NoteDao {
    @Insert(onConflict= OnConflictStrategy.IGNORE)
    void insertData(List<Note> note);

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    void insert(Note note);

    // make function update
    @Update
    void update (Note note);

    // delete function query
    @Delete()
    void delete(Note note);

//    // select id
//    @Query("SELECT * from note ORDER BY id ASC")
//    // memanggil datasource pagging
//    DataSource.Factory<Integer, Note> getAllNotes();

    // use rawQuery
    @RawQuery(observedEntities = Note.class)
    DataSource.Factory<Integer, Note> getAllNote(SupportSQLiteQuery query);


}

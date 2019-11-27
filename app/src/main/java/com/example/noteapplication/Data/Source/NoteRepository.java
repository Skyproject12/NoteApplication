package com.example.noteapplication.Data.Source;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.noteapplication.Data.Module.Note;
import com.example.noteapplication.Data.Source.Locale.NoteDao;
import com.example.noteapplication.Data.Source.Locale.roomDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepository {
    // initial dao
    private NoteDao mNoteDao;
    private ExecutorService executorService;
    public NoteRepository (Application application){
        executorService= Executors.newSingleThreadExecutor();
        roomDatabase db= roomDatabase.getDatabase(application);
        mNoteDao= db.noteDao();

    }

    // initial class notedao
    public LiveData<List<Note>> getAllNotes(){
        return mNoteDao.getAllNotes();

    }

    // call Notedao to insert data
    public void insert(final Note note){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                mNoteDao.insert(note);
            }
        });
    }

    // call Notedao to delete data
    public void delete(final Note note){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                mNoteDao.delete(note);
            }
        });
    }

    // call Notedao to update data
    public void update (final Note note){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                mNoteDao.update(note);
            }
        });
    }

}

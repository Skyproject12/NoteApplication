package com.example.noteapplication.ViewModel.NoteAddUpdate;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.example.noteapplication.Data.Module.Note;
import com.example.noteapplication.Data.Source.NoteRepository;

public class NoteAddUpdateViewModel extends ViewModel {

    private NoteRepository mNoteRepository;

    public NoteAddUpdateViewModel(Application application) {
        mNoteRepository = new NoteRepository(application);

    }

    // call insert
    public void insert(Note note) {
        mNoteRepository.insert(note);

    }

    // call update
    public void update(Note note) {
        mNoteRepository.update(note);

    }

    // call delete
    public void delete(Note note) {
        mNoteRepository.delete(note);

    }

}

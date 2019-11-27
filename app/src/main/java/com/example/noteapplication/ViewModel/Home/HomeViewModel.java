package com.example.noteapplication.ViewModel.Home;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.noteapplication.Data.Module.Note;
import com.example.noteapplication.Data.Source.NoteRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private NoteRepository mNoteRepository;

    // definition noteRepository
    public HomeViewModel(Application application){
        mNoteRepository= new NoteRepository(application);

    }
    // getAllNote from repository
    public LiveData<List<Note>> getAllNotes(){
        return mNoteRepository.getAllNotes();

    }
}

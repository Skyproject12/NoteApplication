package com.example.noteapplication.ViewModel.Home;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

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
//    public LiveData<PagedList<Note>> getAllNotes(){
//        // change to use Pangging
//        return new LivePagedListBuilder<>(mNoteRepository.getAllNotes(),20).build();
//        // memberi batas page hanya 20 size
//
//    }
    // get all with RawQuery
    public LiveData<PagedList<Note>> getAllNote(String sort){
        return new LivePagedListBuilder<>(mNoteRepository.getAllNote(sort),20).build();
    }
}

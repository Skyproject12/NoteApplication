package com.example.noteapplication.Ui.Home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.noteapplication.Data.Module.Note;
import com.example.noteapplication.Data.Source.Locale.SortUtils;
import com.example.noteapplication.R;
import com.example.noteapplication.Ui.Insert.NoteAddUpdateActivity;
import com.example.noteapplication.ViewModel.Home.HomeViewModel;
import com.example.noteapplication.ViewModel.ViewModelFactory.ViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Observable;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    //private HomeAdapter adapter;
    private NotePageListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HomeViewModel homeViewModel= obtainViewModel(HomeActivity.this);
        // set value notes in noteObserver use homeViewModel.getAllNotes
        homeViewModel.getAllNote(SortUtils.NEWEST).observe(this, noteObserver);
        adapter= new NotePageListAdapter(HomeActivity.this);
        recyclerView= findViewById(R.id.recycler_note);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        FloatingActionButton fabAdd= findViewById(R.id.fab_note);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ketika id fab di click maka yang di request adalah insert
                if(v.getId()==R.id.fab_note){
                    Intent intent= new Intent(HomeActivity.this, NoteAddUpdateActivity.class);
                    startActivityForResult(intent, NoteAddUpdateActivity.REQUEST_ADD);

                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            // ketika status Request adalah add  dan data tidak kosong
            if(requestCode==NoteAddUpdateActivity.REQUEST_ADD){
                if(resultCode== NoteAddUpdateActivity.RESULT_ADD) {
                    showSnackBarMessage(getString(R.string.added));
                }
             // ketika request update
            } else if(requestCode== NoteAddUpdateActivity.REQUEST_UPDATE){
                // status yang diset di NoteAddUpdateActivity
                if(resultCode==NoteAddUpdateActivity.RESULT_UPDATE){
                    showSnackBarMessage(getString(R.string.change));
                }
                else if(resultCode==NoteAddUpdateActivity.RESULT_DELETE){
                    showSnackBarMessage(getString(R.string.delete));
                }
            }
        }
    }

    // change select data to pagedList
    private final Observer<PagedList<Note>> noteObserver= new Observer<PagedList<Note>>() {
        @Override
        public void onChanged(PagedList<Note> notes) {
            // submit list merupakan suatu funsgsi dari PagedList untuk menambahkan data ke dalam adapter
            adapter.submitList(notes);
        }
    };

//    private final Observer<List<Note>> noteObserver= new Observer<List<Note>>() {
//        @Override
//        public void onChanged(List<Note> notes) {l
//            if(notes!=null){
//                // set list use data from viewmodel
//
//            }
//        }
//    };

    HomeViewModel obtainViewModel(AppCompatActivity activity){
        ViewModelFactory factory= ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity,factory).get(HomeViewModel.class);

    }

    //set snackbar
    private void showSnackBarMessage(String message){
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();

    }
}

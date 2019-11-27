package com.example.noteapplication.Ui.Home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.noteapplication.Data.Module.Note;
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
    private HomeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HomeViewModel homeViewModel= obtainViewModel(HomeActivity.this);
        // set value notes in noteObserver use homeViewModel.getAllNotes
        homeViewModel.getAllNotes().observe(this, noteObserver);
        adapter= new HomeAdapter(HomeActivity.this);
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

    private final Observer<List<Note>> noteObserver= new Observer<List<Note>>() {
        @Override
        public void onChanged(List<Note> notes) {
            if(notes!=null){
                // set list use data from viewmodel
                adapter.setListNote(notes);
            }
        }
    };

    HomeViewModel obtainViewModel(AppCompatActivity activity){
        ViewModelFactory factory= ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity,factory).get(HomeViewModel.class);

    }

    //set snackbar
    private void showSnackBarMessage(String message){
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();

    }
}

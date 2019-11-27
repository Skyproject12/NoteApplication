package com.example.noteapplication.Ui.Insert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.noteapplication.Base.DateHelper;
import com.example.noteapplication.Data.Module.Note;
import com.example.noteapplication.R;
import com.example.noteapplication.ViewModel.NoteAddUpdate.NoteAddUpdateViewModel;
import com.example.noteapplication.ViewModel.ViewModelFactory.ViewModelFactory;

public class NoteAddUpdateActivity extends AppCompatActivity {

    private EditText edtTitle;
    private EditText edtDescription;

    private boolean isEdit= false;
    // membuat status update add atau yang lainnya
    public static final int REQUEST_ADD= 100;
    public static final int RESULT_ADD= 101;
    public static final int REQUEST_UPDATE= 200;
    public static final int RESULT_UPDATE=201;
    public static final int RESULT_DELETE= 301;

    private Note note;
    private int position;

    private final int ALERT_DIALOG_CLOSE=10;
    private final int ALERT_DIALOG_DELETE=20;

    private static final String EXTRA_NOTE= "extra_note";
    private static final String EXTRA_POSITION= "extra_position";

    private NoteAddUpdateViewModel noteAddUpdateViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add_update);
        noteAddUpdateViewModel= obtainViewModel(NoteAddUpdateActivity.this);

        edtTitle= findViewById(R.id.edt_title);
        edtDescription= findViewById(R.id.edt_description);
        Button btnSubmit= findViewById(R.id.btn_submit);

        // get data from main when intent
        note= getIntent().getParcelableExtra(EXTRA_NOTE);
        // check apakah data kosong atau tidak (untuk menentukan update atau insert)
        if(note!=null){
            position= getIntent().getIntExtra(EXTRA_POSITION, 0);
            // membuat status edit true (untuk mengetaui edit atau insert)
            isEdit= true;
        }
        else{
            note= new Note();
        }

        // to set Title in actionbar
        String actionBarTitle;
        String btnTitle;

        // ketika statusnya update
        if(isEdit){
            // set title in action title bar
            actionBarTitle= getString(R.string.change);
            btnTitle= getString(R.string.update);

            if(note!=null){
                edtTitle.setText(note.getTitle());
                edtDescription.setText(note.getDescription());

            }
        }
        else{
            // set title in action title bar
            actionBarTitle= getString(R.string.add);
            btnTitle= getString(R.string.save);

        }
        // set toolbar use variable actiontitle
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // set text buttonsubmit
        btnSubmit.setText(btnTitle);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get from editText
                String title= edtTitle.getText().toString();
                String description= edtDescription.getText().toString().trim();
                if(title.isEmpty()){
                    // ketika editTitle kosong
                    edtTitle.setError(getString(R.string.empty));
                }
                else if(description.isEmpty()){
                    edtDescription.setError(getString(R.string.empty));
                }
                else{
                    // save title in note class
                    note.setTitle(title);
                    note.setDescription(description);
                    Intent intent= new Intent();
                    intent.putExtra(EXTRA_NOTE, note);
                    intent.putExtra(EXTRA_POSITION, position);
                    if(isEdit){
                        // melakukan update database melalui viewmodel
                        noteAddUpdateViewModel.update(note);
                        // mengeset status intent adalah update
                        setResult(RESULT_UPDATE, intent);
                        // mengirim hasil intent ke activity sebelumnya
                        finish();
                    }
                    else{
                        // mengambil currentDate
                        note.setDate(DateHelper.getCurrentDate());
                        // melakukan insert
                        noteAddUpdateViewModel.insert(note);
                        setResult(RESULT_ADD, intent);
                        finish();
                    }
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(isEdit){
            getMenuInflater().inflate(R.menu.menu_form, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:
                // show alert with status alert is delete
                showAlertDialog(ALERT_DIALOG_DELETE);
                break;
            case android.R.id.home:
                // show alert with status alert is close
                showAlertDialog(ALERT_DIALOG_CLOSE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showAlertDialog(ALERT_DIALOG_CLOSE);

    }

    private void showAlertDialog(int type){
        // mengeset status alert
        final boolean isDialogClose= type== ALERT_DIALOG_CLOSE;
        String dialogTitle;
        String dialogMessage;
        // ketika status close
        if(isDialogClose){
            dialogTitle= getString(R.string.cancel);
            dialogMessage= getString(R.string.message_cancel);
        }
        else{
            dialogMessage= getString(R.string.message_delete);
            dialogTitle= getString(R.string.delete);

        }
        AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(this);
        // set title in dialog interface
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder.setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // ketika dialog close
                        if(isDialogClose){
                            // maka akan melakukan finis atau pergi ke halaman home
                            finish();
                        }
                        else{
                            noteAddUpdateViewModel.delete(note);
                            Intent intent= new Intent();
                            intent.putExtra(EXTRA_POSITION, position);
                            // set status result ketika finish
                            setResult(RESULT_DELETE, intent);
                            finish();
                        }
                    }
                }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // ketika cancel maka akan menutup dialog interface
                dialog.cancel();
            }
        });
        AlertDialog alertDialog= alertDialogBuilder.create();
        alertDialog.show();
    }

    private static NoteAddUpdateViewModel obtainViewModel(AppCompatActivity activity){
        ViewModelFactory factory= ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(NoteAddUpdateViewModel.class);

    }
}

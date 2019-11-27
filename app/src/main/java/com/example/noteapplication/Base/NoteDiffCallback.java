package com.example.noteapplication.Base;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.noteapplication.Data.Module.Note;

import java.util.List;

// untuk melakukan pengecekan apakah terdapat perubahan pada isi dari array note
public class NoteDiffCallback extends DiffUtil.Callback {

    private final List<Note> mOldNoteList;
    private final List<Note> mNewNoteList;

    // make constructor for variable notelist
    public NoteDiffCallback(List<Note> mOldNoteList, List<Note> mNewNoteList) {
        this.mOldNoteList = mOldNoteList;
        this.mNewNoteList = mNewNoteList;
    }

    @Override
    public int getOldListSize() {
        return mOldNoteList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewNoteList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        // memberikan return dimana mOld sama dengan mNew berdasarkan id
        return mOldNoteList.get(oldItemPosition).getId()== mNewNoteList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        // menampng hasil arraylist ke dalam variable oldEmployee
        final Note oldEmployee= mOldNoteList.get(oldItemPosition);
        final Note newEmployee= mNewNoteList.get(newItemPosition);
        // mengecek title dari note sudah sama atau belum
        return oldEmployee.getTitle().equals(newEmployee.getTitle());

    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}

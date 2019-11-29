package com.example.noteapplication.Ui.Home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapplication.Data.Module.Note;
import com.example.noteapplication.R;
import com.example.noteapplication.Ui.Insert.NoteAddUpdateActivity;

// initial new adapter for pagedList
public class NotePageListAdapter extends PagedListAdapter<Note, NotePageListAdapter.ViewHolder> {

    private final Activity activity;

    NotePageListAdapter( Activity activity) {
        super(DIFF_CALLBACK);
        this.activity = activity;
    }

    // custom class super
    private static DiffUtil.ItemCallback<Note> DIFF_CALLBACK= new DiffUtil.ItemCallback<Note>() {
        // melakukan pengecekan apakah data baru atau lama
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.equals(newItem);

        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Note note= getItem(position);
        if(note!=null){
            holder.tvTitle.setText(note.getTitle());
            holder.tvDate.setText(note.getDate());
            holder.tvDescription.setText(note.getDescription());
            holder.cvNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(activity, NoteAddUpdateActivity.class);
                    intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, holder.getAdapterPosition());
                    intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, note);
                    activity.startActivityForResult(intent, 200);
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvDescription;
        TextView tvDate;
        CardView cvNote;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle= itemView.findViewById(R.id.tv_item_title);
            tvDescription= itemView.findViewById(R.id.tv_item_description);
            tvDate= itemView.findViewById(R.id.tv_item_date);
            cvNote= itemView.findViewById(R.id.cv_item_note);

        }
    }
}

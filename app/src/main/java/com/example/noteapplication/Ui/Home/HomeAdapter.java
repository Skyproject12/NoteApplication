package com.example.noteapplication.Ui.Home;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapplication.Base.NoteDiffCallback;
import com.example.noteapplication.Data.Module.Note;
import com.example.noteapplication.R;
import com.example.noteapplication.Ui.Insert.NoteAddUpdateActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private final ArrayList<Note> listNote= new ArrayList<>();
    private final Activity activity;

    public HomeAdapter(Activity activity) {
        this.activity = activity;
    }

    // mengeset arraylist ke dalam adapter
    void setListNote(List<Note> listNote){
        final NoteDiffCallback diffCallback= new NoteDiffCallback(this.listNote, listNote);
        final DiffUtil.DiffResult diffResult= DiffUtil.calculateDiff(diffCallback);
        this.listNote.clear();
        this.listNote.addAll(listNote);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.tvTitle.setText(listNote.get(position).getTitle());
        holder.tvDate.setText(listNote.get(position).getDate());
        holder.tvDescription.setText(listNote.get(position).getDescription());
        holder.cvNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(activity, NoteAddUpdateActivity.class);
                intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, holder.getAdapterPosition());
                intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, listNote.get(holder.getAdapterPosition()));
                activity.startActivityForResult(intent, 200);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listNote.size();
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

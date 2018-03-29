package com.ahmedadel.noteapp.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahmedadel.noteapp.R;
import com.ahmedadel.noteapp.model.Note;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ahmed Adel on 3/28/18.
 * <p>
 * NoteAdapter the adapter of notes recycler view list.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<Note> noteList;

    public NoteAdapter(List<Note> noteList) {
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(noteList.get(position));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void setNoteList(List<Note> newNoteList) {
        if (newNoteList != null) {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new NoteDiffCallback(noteList, newNoteList));
            noteList.clear();
            noteList.addAll(newNoteList);
            diffResult.dispatchUpdatesTo(this);
        } else {
            noteList.clear();
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.note_item_list_title_tv)
        TextView name;
        @BindView(R.id.note_item_list_description_tv)
        TextView description;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Note note) {
            name.setText(note.getName());
            description.setText(note.getDescription());
        }
    }

}

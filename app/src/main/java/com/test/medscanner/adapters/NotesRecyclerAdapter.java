package com.test.medscanner.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.medscanner.R;
import com.test.medscanner.util.Note;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.NotesViewHolder> {
    private ArrayList<Note> mNotesSet;
    private HashMap<Integer, Note> mSelectedNotesSet;

    public NotesRecyclerAdapter() {
        mNotesSet = new ArrayList<>();
        mSelectedNotesSet = new HashMap<>();
    }

    public void addAll(Collection<Note> mNotesSet) {
        this.mNotesSet.addAll(mNotesSet);
        notifyDataSetChanged();
    }

    public void add(Note note) {
        this.mNotesSet.add(note);
        notifyDataSetChanged();
    }

    public void clear() {
        this.mNotesSet.clear();
        this.mSelectedNotesSet.clear();
        notifyDataSetChanged();
    }

    public void remove(int position) {
        this.mNotesSet.remove(position);
        this.mSelectedNotesSet.remove(position);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_note_layout, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.bind(mNotesSet.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mNotesSet.size();
    }

    public HashMap<Integer, Note> getmSelectedNotesSet() {
        return mSelectedNotesSet;
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private CheckBox checkBox;

        NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.delete_check_box);
            textView = itemView.findViewById(R.id.note_text);
        }

        void bind(Note note, int position) {
            checkBox.setChecked(false);
            checkBox.setOnCheckedChangeListener(
                    (compoundButton, b) -> {
                        if (b) {
                            mSelectedNotesSet.put(position, note);
                        } else {
                            mSelectedNotesSet.remove(position);
                        }
                    });
            textView.setText(note.getmNoteText());
        }
    }
}

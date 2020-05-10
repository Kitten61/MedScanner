package com.test.medscanner;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.test.medscanner.adapters.NotesRecyclerAdapter;
import com.test.medscanner.presenter.NotesPresenter;
import com.test.medscanner.presenterImpl.NotesPresenterImpl;
import com.test.medscanner.util.Note;
import com.test.medscanner.view.NotesView;

import java.util.List;

import io.realm.Realm;

public class NotesFragment extends Fragment implements NotesView {
    private NotesPresenter mPresenter;
    private NotesRecyclerAdapter mNotesAdapter;
    private FloatingActionButton addNoteButton;

    private ViewGroup mPlaceholder;

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.options_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.deleteSelected) {
            mPresenter.removeNotes(mNotesAdapter.getmSelectedNotesSet());
        }

        return super.onOptionsItemSelected(item);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mPresenter = new NotesPresenterImpl(this);

        mPlaceholder = view.findViewById(R.id.note_placeholder);

        RecyclerView notesView = view.findViewById(R.id.notes_recycler);
        notesView.setLayoutManager(new LinearLayoutManager(getContext()));
        mNotesAdapter = new NotesRecyclerAdapter();
        Realm.init(getContext());
        notesView.setAdapter(mNotesAdapter);
        mPresenter.loadNotes();

        addNoteButton = view.findViewById(R.id.floatingActionButton3);
        initAddNote();
    }

    private void initAddNote() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle(R.string.add_note_dialog);
        dialog.setContentView(R.layout.note_add);

        final EditText noteText = dialog.findViewById(R.id.add_note_text);
        Button noteButton = dialog.findViewById(R.id.add_note_button);

        noteButton.setOnClickListener(view -> {
            String text = noteText.getText().toString();
            mPresenter.addNote(text);
            dialog.cancel();
            noteText.setText("");
        });

        addNoteButton.setOnClickListener(view -> dialog.show());
    }

    @Override
    public void updateRecycler(List<Note> list) {
        if (list.size() == 0) {
            mPlaceholder.setVisibility(View.VISIBLE);
        } else {
            mPlaceholder.setVisibility(View.INVISIBLE);
        }
        mNotesAdapter.clear();
        mNotesAdapter.addAll(list);
    }

    @Override
    public void showLoadError(int error) {
        Toast.makeText(getContext(), getString(error), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNotesChanged() {
        mPresenter.loadNotes();
    }
}
package com.test.medscanner.view;

import com.test.medscanner.util.Note;

import java.util.List;

public interface NotesView {
    void updateRecycler(List<Note> list);
    void showLoadError(int error);
    void onNotesChanged();
}
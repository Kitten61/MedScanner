package com.test.medscanner.presenter;

import com.test.medscanner.util.Note;

import java.util.HashMap;

public interface NotesPresenter {
    void addNote(String noteText);
    void loadNotes();
    void removeNotes(HashMap<Integer, Note> notes);
}

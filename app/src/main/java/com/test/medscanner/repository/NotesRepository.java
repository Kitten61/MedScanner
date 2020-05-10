package com.test.medscanner.repository;


import com.test.medscanner.util.Note;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface NotesRepository {
    Single<List<Note>> loadNotesFromRealm();
    Single<Object> addNoteInRealm(String text);
    Single<Object> removeNotesFromRealm(HashMap<Integer, Note> notes);
}

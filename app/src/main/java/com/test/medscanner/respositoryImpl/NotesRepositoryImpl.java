package com.test.medscanner.respositoryImpl;

import com.test.medscanner.repository.NotesRepository;
import com.test.medscanner.util.Note;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import io.realm.Realm;
import io.realm.RealmResults;

public class NotesRepositoryImpl implements NotesRepository {
    @Override
    public Single<List<Note>> loadNotesFromRealm() {
        return Single.create(
                subscriber -> {
                    Realm realm = Realm.getDefaultInstance();
                    RealmResults<Note> noteRealmResults = realm.where(Note.class).findAll();
                    List<Note> result = realm.copyFromRealm(noteRealmResults);
                    subscriber.onSuccess(result);
                }
        );
    }

    @Override
    public Single<Object> addNoteInRealm(String text) {
        return Single.create(
                subscriber -> {
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(r -> r.createObject(Note.class).setmNoteText(text));
                    subscriber.onSuccess(true);
                }
        );
    }

    @Override
    public Single<Object> removeNotesFromRealm(HashMap<Integer, Note> notes) {
        return Single.create(
                subscriber -> {
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(
                            r -> {
                                for (Map.Entry<Integer, Note> entry: notes.entrySet()) {
                                    realm.where(Note.class).equalTo("mNoteText", entry.getValue().getmNoteText()).findFirst().deleteFromRealm();
                                }
                            }
                    );
                    subscriber.onSuccess(true);
                }
        );
    }
}

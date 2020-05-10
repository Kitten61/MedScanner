package com.test.medscanner.presenterImpl;

import android.util.Log;

import com.test.medscanner.R;
import com.test.medscanner.presenter.NotesPresenter;
import com.test.medscanner.repository.NotesRepository;
import com.test.medscanner.respositoryImpl.NotesRepositoryImpl;
import com.test.medscanner.util.Note;
import com.test.medscanner.view.NotesView;

import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NotesPresenterImpl implements NotesPresenter {
    private NotesView mView;
    private NotesRepository mRepository;

    public NotesPresenterImpl(NotesView mView) {
        this.mView = mView;
        mRepository = new NotesRepositoryImpl();
    }

    @Override
    public void addNote(String noteText) {
        Disposable disposable = mRepository.addNoteInRealm(noteText)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        s -> {
                            mView.onNotesChanged();
                        },
                        e -> {
                            mView.showLoadError(R.string.add_note_error);
                        }
                );
    }

    @Override
    public void loadNotes() {
        Disposable disposable = mRepository.loadNotesFromRealm()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        s -> {
                            mView.updateRecycler(s);
                        },
                        e -> {
                            mView.showLoadError(R.string.load_notes_error);
                        }
                );
    }

    @Override
    public void removeNotes(HashMap<Integer, Note> notes) {
        mRepository.removeNotesFromRealm(notes)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        s -> {
                            mView.onNotesChanged();
                        },
                        e -> {
                            Log.e("Error", e.getMessage());
                            mView.showLoadError(R.string.delete_notes_error);
                        }
                );
    }
}

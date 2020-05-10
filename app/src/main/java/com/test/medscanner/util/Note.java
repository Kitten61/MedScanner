package com.test.medscanner.util;

import io.realm.RealmObject;

public class Note extends RealmObject {

    private String mNoteText;

    public String getmNoteText() {
        return mNoteText;
    }

    public void setmNoteText(String mNoteText) {
        this.mNoteText = mNoteText;
    }
}

package com.bytebala.notes.persistence;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.bytebala.notes.async.DeleteAsyncTask;
import com.bytebala.notes.async.InsertAsyncTask;
import com.bytebala.notes.async.UpdateAsyncTask;
import com.bytebala.notes.models.Note;

import java.util.List;

public class NoteRepository {

    //Repository class is most effective when handling different data sources
    //It is best to use when you have different data sources
    //Note that in this application we have only single data source, which is simply SQLite database.


    //All the methods responsible for calling DAO
    private NoteDatabase mNoteDatabase;

    public NoteRepository(Context context) {
        mNoteDatabase = NoteDatabase.getInstance(context);
    }

    public void insertNoteTask (Note note) {
        new InsertAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }

    public void updateNote (Note note) {
        new UpdateAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }

    public void deleteNote (Note note) {
        new DeleteAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }

    //We need to retriew notes
    //It will return LiveData object
    public LiveData<List<Note>> retrieveNotesTask() {
        return mNoteDatabase.getNoteDao().getNotes();
    }
}

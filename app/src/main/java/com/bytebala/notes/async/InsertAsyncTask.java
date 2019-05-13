package com.bytebala.notes.async;

import android.os.AsyncTask;
import android.util.Log;

import com.bytebala.notes.models.Note;
import com.bytebala.notes.persistence.NoteDAO;

// Good for executing single task. Execute single thing in the background and then destroy

public class InsertAsyncTask extends AsyncTask<Note, Void, Void> {

  private static final String TAG = "InsertAsyncTask";

  private NoteDAO mNoteDao;

  public InsertAsyncTask(NoteDAO dao) {
    mNoteDao = dao;
  }

  @Override
  protected Void doInBackground(Note... notes) {
    Log.d(TAG, "doInBackground: thread" + Thread.currentThread().getName());

    mNoteDao.insertNotes(notes);
    return null;
  }
}

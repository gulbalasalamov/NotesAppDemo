package com.bytebala.notes.async;

import android.os.AsyncTask;
import android.util.Log;

import com.bytebala.notes.models.Note;
import com.bytebala.notes.persistence.NoteDAO;

// Good for executing single task. Execute single thing in the background and then destroy

public class DeleteAsyncTask extends AsyncTask<Note, Void, Void> {

  private static final String TAG = "DeleteAsyncTask";

  private NoteDAO mNoteDao;

  public DeleteAsyncTask(NoteDAO dao) {
    mNoteDao = dao;
  }

  @Override
  protected Void doInBackground(Note... notes) {
    Log.d(TAG, "doInBackground: thread" + Thread.currentThread().getName());

    mNoteDao.delete(notes);
    return null;
  }
}

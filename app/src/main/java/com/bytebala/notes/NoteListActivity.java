package com.bytebala.notes;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.bytebala.notes.adapters.NotesRecyclerAdapter;
import com.bytebala.notes.models.Note;
import com.bytebala.notes.persistence.NoteRepository;
import com.bytebala.notes.utils.VerticalSpacingItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends AppCompatActivity
    implements NotesRecyclerAdapter.OnNoteListener, View.OnClickListener {

  private static final String TAG = "NoteListActivity";

  // UI components
  private RecyclerView mRecyclerView; // m global // without m inside method something local

  // Vars - not view not widget
  private ArrayList<Note> mNotes = new ArrayList<>();
  private NotesRecyclerAdapter mNoteRecyclerAdapter;
  private NoteRepository mNoteRepository;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_notes_list);
    mRecyclerView = findViewById(R.id.recyclerView);

    findViewById(R.id.fab).setOnClickListener(this);

    mNoteRepository = new NoteRepository(this);
    initRecycleView();
    retrieveNotes();
    //        insertFakeNoteS();

    Log.d(TAG, "onCreate: thread: " + Thread.currentThread().getName());
    setSupportActionBar((Toolbar) findViewById(R.id.notes_toolbar));
    setTitle("Notes");

    /**
     * Note note = new Note("some title","some content","some timestamp"); Note note2 = new Note();
     * note2.setTitle("some other title"); note2.setContent("some other content");
     * note2.setTimeStamp("some other time");
     *
     * <p>Log.d(TAG, "onCreate: my note: " + note.getTitle()); //Log.d(TAG, "onCreate: this is a
     * test log output.");
     */
  }

  private void retrieveNotes() {
    // using observer to observe changes to LiveData objects
    // attachig observer to LiveData object returned from database
    // looks like listening to database
    // works in background thread
    mNoteRepository
        .retrieveNotesTask()
        .observe(
            this,
            new Observer<List<Note>>() {
              @Override
              public void onChanged(@Nullable List<Note> notes) {
                if (mNotes.size() > 0) {
                  mNotes.clear();
                }
                if (notes != null) {
                  mNotes.addAll(notes);
                }
                mNoteRecyclerAdapter.notifyDataSetChanged();
              }
            });
  }

  // Inserting new note is different than retrieving data, which was LiveData.
  // To insert a note , use AsyncTask in a backgroind thread

  private void insertFakeNoteS() {
    for (int i = 0; i < 1000; i++) {
      Note note = new Note();
      note.setTitle("title #: " + i);
      note.setContent("content #: " + i);
      note.setTimestamp("Jan 2019");
      mNotes.add(note);
    }
    mNoteRecyclerAdapter.notifyDataSetChanged();
  }

  private void initRecycleView() {
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    mRecyclerView.setLayoutManager(linearLayoutManager);
    VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
    new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
    mRecyclerView.addItemDecoration(itemDecorator);
    mNoteRecyclerAdapter = new NotesRecyclerAdapter(mNotes, this);
    mRecyclerView.setAdapter(mNoteRecyclerAdapter);
  }

  @Override
  public void onNoteClick(int position) {

    // Access note selected
    Log.d(TAG, "onNoteClick: clicked: " + position);

    Intent intent = new Intent(this, NoteActivity.class);
    intent.putExtra("selected_note", mNotes.get(position));
    startActivity(intent);

    // intent.putExtra("some object","something else"); // use parcelable instead
    /*
    Parcelable implementation is a way of packaging objects and then objects can be added to bundle.
    Custom data classes(models) cannot be attached to bundle by default.
    They must be declared as parcelable to be attached bundle
    You should not attach large data sets to bundle. (for exp arraylist of 1000s items)
    */
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent(this, NoteActivity.class);
    startActivity(intent);
  }

  private void deleteNote(Note note) {
    mNotes.remove(note);
    mNoteRecyclerAdapter.notifyDataSetChanged();
    mNoteRepository.deleteNote(note);
  }

  // ItemTouchHelper to detect when list items swiped in RecyclerView and when Swyped deleted
  private ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
      new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        // onMove can be used to re-arrange the items. You can literally grap the list items and
        // drag around and re-arrange them.
        public boolean onMove(
            @NonNull RecyclerView recyclerView,
            @NonNull RecyclerView.ViewHolder viewHolder,
            @NonNull RecyclerView.ViewHolder viewHolder1) {
          return false;
        }

        @Override
        // onSwiped to swipe
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
          deleteNote(mNotes.get(viewHolder.getAdapterPosition()));
        }
      };
}

package com.bytebala.notes.adapters;

import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bytebala.notes.R;
import com.bytebala.notes.models.Note;
import com.bytebala.notes.utils.TimeUtility;

import java.util.ArrayList;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder> {

  private static final String TAG = "NotesRecyclerAdapter";
  private ArrayList<Note> mNotes = new ArrayList<>();
  /* Create a data structure to hold all those notes
   *  ArrayList is essentially same as a regular list of an array but it automatically adapts to size
   * So we can dynamically add/remove notes. Whereas regular array you can't do that  */
  private OnNoteListener mOnNoteListener;

  public NotesRecyclerAdapter(ArrayList<Note> notes, OnNoteListener onNoteListener) {
    // I prefer global objects with m, whereas local objects no m.
    this.mNotes = notes;
    this.mOnNoteListener = onNoteListener;
  }

  // Instantiate a ViewHolder object
  // Pass a view object to constructor of a ViewHolder
  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view =
        LayoutInflater.from(viewGroup.getContext())
            .inflate(R.layout.layout_note_list_item, viewGroup, false);
    return new ViewHolder(view, mOnNoteListener);
  }

  // You get fields
  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    try {
        String month = mNotes.get(i).getTimestamp().substring(0,2);
        month = TimeUtility.getMonthFromNumber(month);
        String year = mNotes.get(i).getTimestamp().substring(3);
        String timestamp = month + " " + year;
        viewHolder.timestamp.setText(timestamp);
        viewHolder.title.setText(mNotes.get(i).getTitle());
    } catch (NullPointerException e) {
      Log.e(TAG, "onBindViewHolder: NullPointerException" + e.getMessage());
    }



  }

  @Override
  public int getItemCount() {
    return mNotes.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView title, timestamp;
    OnNoteListener onNoteListener;

    public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
      super(itemView);
      title = itemView.findViewById(R.id.note_title);
      timestamp = itemView.findViewById(R.id.note_timestamp);
      this.onNoteListener = onNoteListener;

      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      onNoteListener.onNoteClick(getAdapterPosition());
    }
  }

  public interface OnNoteListener {
    void onNoteClick(int position);
  }
}

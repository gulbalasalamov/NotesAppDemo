package com.bytebala.notes.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bytebala.notes.models.Note;

import java.util.List;

@Dao
public interface NoteDAO {

    //Room Persistance Data Access Objects

    @Insert
    long[] insertNotes(Note... notes); // return a long array
    // ... elipsies can be donated for list of notes. Note[]

    @Query("SELECT * FROM notes")
    //Return type of LiveData and it will return list of Note objects.
    LiveData<List<Note>> getNotes();

    @Delete
    //You don't have to return anything. You can use void but optionally you can return to represent how many rows were updated.
    int delete(Note... notes);

    @Update
    //How many rows were affected by update
    int update(Note[] notes);

    //Custom querry examples

    @Query("SELECT * FROM notes WHERE id = :id")
    List<Note> getNoteWithCustomQuerry(int id);

    @Query("SELECT * FROM notes WHERE title LIKE :title")
    List<Note> getNoteWithCustomQuerry2(String title);

    /*
    "Elizebeth" Suppose you"re searching title elizebert and you dont know how to spell
    getNoteWithCustomQuerry2("eli*");
     */

}

package com.bytebala.notes.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.bytebala.notes.models.Note;

@Database(
    entities = {Note.class},
    version = 2)
// Change version whenever making change updating database structures, tables, entities

/* Keep track of schema for version
   Upon compilation, Room exports your database's schema information into a JSON file.
   To export schema, set the room.schemaLocation annotation processor property in build.gradle file
*/

public abstract class NoteDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "notes_db";

    //Implement a Singleton Pattern to create database instance
    private static NoteDatabase instance;

    static NoteDatabase getInstance(final Context context) {
        if (instance==null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    NoteDatabase.class,
                    DATABASE_NAME
            ).build();
        }return instance;
    }
    // Singleton Pattern is what and why we used?
    //Singleton Pattern refers to instance, creating an instance of an object
    //What this helps to do is make sure that a whole bunch of same object laying around the memory.
    //It helps to keep an instance or one version of object and it is just a way to optimize memory usage basically.

    public abstract NoteDAO getNoteDao();
}

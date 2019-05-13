package com.bytebala.notes.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "notes")
// Creating a Relational SQLite Database and giving table of notes and it will contain notes objects
public class Note implements Parcelable {

  @PrimaryKey(autoGenerate = true)
  private int id;

  @ColumnInfo(name = "title")
  private String title;

  @ColumnInfo(name = "content")
  private String content;

  @ColumnInfo(name = "timestamp")
  private String timestamp;



  public Note(String title, String content, String timestamp) {
    this.title = title;
    this.content = content;
    this.timestamp = timestamp;
  }

  // We need to ignore it to tell Room which constructor we want to use
  @Ignore
  public Note() {}

  protected Note(Parcel in) {
    id = in.readInt();
    title = in.readString();
    content = in.readString();
    timestamp = in.readString();
  }

  public static final Creator<Note> CREATOR =
      new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
          return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
          return new Note[size];
        }
      };

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return "Note{"
        + "id="
        + id
        + ", title='"
        + title
        + '\''
        + ", content='"
        + content
        + '\''
        + ", timestamp='"
        + timestamp
        + '\''
        + '}';
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(id);
    dest.writeString(title);
    dest.writeString(content);
    dest.writeString(timestamp);
  }

  // After Room Parcelable becomes ->

  // Before Room Parcelalble implementation
  /*
  protected Note(Parcel in) {
    title = in.readString();
    content = in.readString();
    timestamp = in.readString();
  }

  public static final Creator<Note> CREATOR =
      new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
          return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
          return new Note[size];
        }
      };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(title);
    parcel.writeString(content);
    parcel.writeString(timestamp);
  }

   @Override
  public String toString() {
    return "Note{"
        + "title='"
        + title
        + '\''
        + ", content='"
        + content
        + '\''
        + ", timeStamp='"
        + timestamp
        + '\''
        + '}';
  }
   */
}

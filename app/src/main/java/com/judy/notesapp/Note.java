package com.judy.notesapp;

/**
 * Created by Judy T Raj on 25-10-2016.
 */

public class Note {
    int id;
   String note;

    public void setId(int id) {
        this.id = id;
    }

    public Note() {
    }

    public Note(int id,String note) {
        this.id = id;
        this.note=note;
    }

    public  Note(String note){

        this.note=note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public int getId() {
        return id;
    }
}

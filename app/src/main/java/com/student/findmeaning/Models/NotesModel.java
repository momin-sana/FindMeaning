package com.student.findmeaning.Models;

public class NotesModel {
    int id;
    String word;
    String note;

    public NotesModel(){
    }
    public NotesModel(int id, String word, String note) {
        this.id = id;
        this.word = word;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

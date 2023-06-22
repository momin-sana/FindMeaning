package com.student.findmeaning;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.student.findmeaning.Models.NotesModel;

import java.util.ArrayList;
import java.util.List;

public class NotesDBHandler extends SQLiteOpenHelper {
    SQLiteDatabase db;
    ContentValues contentValues;
    Cursor cursor;
    private static final String DB_NAME = "notes_db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "notesTable";

    private static final String ID_COL = "id";
    private static final String TASK_WORD = "word";
    private static final String WORD_DESCRIPTION = "description";


    public NotesDBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryNotesDbHandler = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + TASK_WORD + " TEXT NOT NULL,"
                + WORD_DESCRIPTION + " TEXT )";

        db.execSQL(queryNotesDbHandler);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addWordNotes(String word, String description){
        db = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(TASK_WORD, word);
        contentValues.put(WORD_DESCRIPTION, description);
        long result = db.insert(TABLE_NAME,null,contentValues);
        return result != -1;
    }

    public List<NotesModel> displayAllNotes(){
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);
        List<NotesModel> notesModelList = new ArrayList<>();

        while (cursor.moveToNext()){
            NotesModel notesModel = new NotesModel();
            notesModel.setId(cursor.getInt(0));
            notesModel.setWord(cursor.getString(1));
            notesModel.setNote(cursor.getString(2));
            notesModelList.add(notesModel);
        }
        cursor.close();
        db.close();
        return notesModelList;
    }

    public void updateNote(NotesModel notesModel, OnDialogCloseListener listener) {
        db = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(TASK_WORD, notesModel.getWord());
        contentValues.put(WORD_DESCRIPTION, notesModel.getNote());

       db.update(TABLE_NAME, contentValues,
               ID_COL + " = ?",
                new String[]{String.valueOf(notesModel.getId())});
       db.close();

        if (listener != null) {
            listener.onDialogClose(null);
        }
    }

    public void deleteNoteById(int id) {
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID_COL + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteAllNotes() {
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}

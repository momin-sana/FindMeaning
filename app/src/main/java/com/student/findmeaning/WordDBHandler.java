package com.student.findmeaning;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.student.findmeaning.Models.BookmarkModel;
import com.student.findmeaning.Models.History;

import java.util.ArrayList;
import java.util.List;

public class WordDBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bookmarks_history.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "BookmarksHistory";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_WORD = "word";
    private static final String COLUMN_IS_BOOKMARK = "isBookmark";


    public WordDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryWordDbHandler = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_WORD + " TEXT,"
                + COLUMN_IS_BOOKMARK + " INTEGER DEFAULT 0)";

        db.execSQL(queryWordDbHandler);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addBookmark(BookmarkModel bookmarkModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Check if the same word already exists in the table
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_WORD + "=?", new String[]{bookmarkModel.getWord()});
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return; // The same word already exists in the table, do nothing
        }
        cursor.close();

        // The same word does not exist in the table, insert the new row
        ContentValues values = new ContentValues();

        values.put(COLUMN_WORD, bookmarkModel.getWord());
        if (bookmarkModel.getId() != -1) {
            values.put(COLUMN_ID, bookmarkModel.getId());
        }
        values.put(COLUMN_IS_BOOKMARK, 1); // 1 = true which is for a bookmark entry

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void addHistory(History history) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Check the number of rows in the table
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
        cursor.moveToFirst();
        int rowCount = cursor.getInt(0);
        cursor.close();

        // If the number of rows exceeds 100, delete the oldest row
        if (rowCount >= 100) {
            db.delete(TABLE_NAME, COLUMN_ID + " = (SELECT MIN(" + COLUMN_ID + ") FROM " + TABLE_NAME + ")", null);
        }

        // Insert the new row into the table
        values.put(COLUMN_WORD, history.getWord());
        values.put(COLUMN_IS_BOOKMARK, 0); // 1 = false which is for a history entry
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<BookmarkModel> getAllBookmarks() {
        List<BookmarkModel> bookmarks = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursorBookmark = db.rawQuery(" SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_IS_BOOKMARK + " = 1 ", null);
                if (cursorBookmark.moveToFirst()){
            do{
                @SuppressLint("Range") int id = cursorBookmark.getInt(cursorBookmark.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String word = cursorBookmark.getString(cursorBookmark.getColumnIndex(COLUMN_WORD));
                bookmarks.add(new BookmarkModel(id, word));
            } while(cursorBookmark.moveToNext());

        }
        cursorBookmark.close();
        return bookmarks;
    }

    public List<History> getAllHistory(){
        List<History> historyList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursorHistory = db.rawQuery(" SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_IS_BOOKMARK + " = 0 ", null);
        if(cursorHistory.moveToFirst()){
            do{
                @SuppressLint("Range") int id = cursorHistory.getInt(cursorHistory.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String word = cursorHistory.getString(cursorHistory.getColumnIndex(COLUMN_WORD));
                historyList.add(new History(id, word));
            }while(cursorHistory.moveToNext());
        }
        cursorHistory.close();
        return historyList;
    }

    public void editWord(int id, String word) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_WORD, word);
        db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteWord(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteBookmark(){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = COLUMN_IS_BOOKMARK + " =? ";
        String[] whereArg = {"1"};
        int bookmarkDeleted = db.delete(TABLE_NAME,whereClause,whereArg);
        Log.d("TAG", "deleteBookmark: " + bookmarkDeleted);
    }

    public boolean isWordBookmarked(String word) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                null,
                COLUMN_WORD + " = ? AND " + COLUMN_IS_BOOKMARK + " = ?",
                new String[]{word, "1"},
                null,
                null,
                null);

        boolean isBookmarked = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return isBookmarked;
    }

    public int getWordId(String word) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_WORD + " = ?";
        String[] selectionArgs = {word};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);


        int wordId = -1;
        if (cursor.moveToFirst()) {
            wordId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
        }

        cursor.close();
        db.close();

        return wordId;
    }
}

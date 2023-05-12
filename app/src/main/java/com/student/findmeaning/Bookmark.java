package com.student.findmeaning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.student.findmeaning.Adapters.BookmarkAdapter;
import com.student.findmeaning.Models.BookmarkModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bookmark extends AppCompatActivity {
    private TextView textView_pgName;
    private ImageButton backImageBtn;
    private ArrayList<BookmarkModel> bookmarkModelArrayList;
    private WordDBHandler dbHandler;

    public Bookmark(){ }
//    public Bookmark(ArrayList<BookmarkModel> bookmarkModelArrayList, WordDBHandler dbHandler) {
//        this.bookmarkModelArrayList = bookmarkModelArrayList;
//        this.dbHandler = dbHandler;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        backImageBtn = findViewById(R.id.action_bar_back);
        textView_pgName = findViewById(R.id.page_name);
        textView_pgName.setText(R.string.bookmark);

        backImageBtn.setOnClickListener(view -> onBackPressed());

//         display db in list
        dbHandler = new WordDBHandler(getApplicationContext());
        bookmarkModelArrayList = new ArrayList<>();
        BookmarkAdapter bookmarkAdapter = new BookmarkAdapter(bookmarkModelArrayList, getApplicationContext(), dbHandler);
        RecyclerView recyclerView = findViewById(R.id.bookmark_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(bookmarkAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        bookmarkModelArrayList = (ArrayList<BookmarkModel>) dbHandler.getAllBookmarks();
        Collections.reverse(bookmarkModelArrayList);
        bookmarkAdapter.setDataChangedBookmark(bookmarkModelArrayList);
    }

}
package com.student.findmeaning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.student.findmeaning.Adapters.BookmarkAdapter;
import com.student.findmeaning.Models.BookmarkModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bookmark extends AppCompatActivity {
    private TextView textView_pgName,tvEmpty;
    private ImageButton backImageBtn, deleteAllBookmark, deleteBookmark;
    private ArrayList<BookmarkModel> bookmarkModelArrayList = new ArrayList<>();
    private WordDBHandler dbHandler;
    private BookmarkAdapter bookmarkAdapter;
    RecyclerView recyclerView;

    public Bookmark(){ }
//    public Bookmark(ArrayList<BookmarkModel> bookmarkModelArrayList, WordDBHandler dbHandler) {
//        this.bookmarkModelArrayList = bookmarkModelArrayList;
//        this.dbHandler = dbHandler;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        deleteBookmark = findViewById(R.id.action_bar_delete_bookmark);
        deleteBookmark.setVisibility(View.VISIBLE);
        deleteBookmark.setEnabled(false);
        deleteBookmark.setColorFilter(R.color.button_pressed);

        backImageBtn = findViewById(R.id.action_bar_back);
        textView_pgName = findViewById(R.id.page_name);
        textView_pgName.setText(R.string.bookmark);
        tvEmpty = findViewById(R.id.tv_empty);

        backImageBtn.setOnClickListener(view -> onBackPressed());

        //         display db in list
        dbHandler = new WordDBHandler(getApplicationContext());
        bookmarkModelArrayList = new ArrayList<>();
        bookmarkAdapter = new BookmarkAdapter(bookmarkModelArrayList, getApplicationContext(), dbHandler);
        recyclerView = findViewById(R.id.bookmark_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(bookmarkAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        // add new bookmarks
        bookmarkModelArrayList = (ArrayList<BookmarkModel>) dbHandler.getAllBookmarks();
        Collections.reverse(bookmarkModelArrayList);
        bookmarkAdapter.setDataChangedBookmark(bookmarkModelArrayList);

        // delete all bookmarks
        deleteAllBookmark = findViewById(R.id.action_bar_delete_all_bookmark);
        deleteAllBookmark.setVisibility(View.VISIBLE);
        deleteAllBookmark.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(Bookmark.this, view);
            popupMenu.inflate(R.menu.del);
            popupMenu.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_delete_all){
                    dbHandler.deleteBookmark();
                    bookmarkAdapter.setDataChangedBookmark(bookmarkModelArrayList);
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(this, R.string.all_bookmark_deleted, Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });

    }

}
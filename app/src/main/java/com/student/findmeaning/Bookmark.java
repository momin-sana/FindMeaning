package com.student.findmeaning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.student.findmeaning.Adapters.BookmarkAdapter;
import com.student.findmeaning.Models.BookmarkModel;

import java.util.ArrayList;
import java.util.Collections;

public class Bookmark extends AppCompatActivity implements OnBookmarkItemClickListener{
    private TextView textView_pgName;
    private ImageButton backImageBtn, deleteAllBookmark, deleteBookmark;
    private ArrayList<BookmarkModel> bookmarkModelArrayList = new ArrayList<>();
    private WordDBHandler dbHandler;
    RecyclerView recyclerView;

//    public Bookmark(){ }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        backImageBtn = findViewById(R.id.action_bar_back);
        backImageBtn.setOnClickListener(view -> onBackPressed());

        textView_pgName = findViewById(R.id.page_name);
        textView_pgName.setText(R.string.bookmark);

        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.bookmark_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        // Initialize the dbHandler
        dbHandler = new WordDBHandler(getApplicationContext());

        // Create the adapter
        final BookmarkAdapter bookmarkAdapter;
        bookmarkAdapter = new BookmarkAdapter(bookmarkModelArrayList, getApplicationContext(), dbHandler);
        bookmarkAdapter.setOnItemClickListener(this);

        // Set the adapter on the RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(bookmarkAdapter);

        //        Create new bookmarkList & display db in list
        bookmarkModelArrayList = new ArrayList<>();
        bookmarkModelArrayList = (ArrayList<BookmarkModel>) dbHandler.getAllBookmarks();
        Collections.reverse(bookmarkModelArrayList);
        bookmarkAdapter.setDataChangedBookmark(bookmarkModelArrayList);

        // delete all bookmarks
        deleteAllBookmark = findViewById(R.id.action_bar_delete_all_bookmark);
        deleteBookmark = findViewById(R.id.action_bar_delete_bookmark);

        if (bookmarkModelArrayList.isEmpty()){
            deleteAllBookmark.setVisibility(View.INVISIBLE);
            deleteBookmark.setVisibility(View.INVISIBLE);
            Toast.makeText(this, R.string.empty_bookmark, Toast.LENGTH_LONG).show();
        }else{
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

            deleteBookmark.setVisibility(View.VISIBLE);
            deleteBookmark.setOnClickListener(view -> {
                ArrayList<Integer> selectedPositions = bookmarkAdapter.getSelectedPositions();
                ArrayList<Integer> copyPositions = new ArrayList<>(selectedPositions); // Create a copy of the selectedPositions list
                for (int position : copyPositions){
                    bookmarkAdapter.deleteWords(position);
                }
                bookmarkAdapter.setSelectedPositions(new ArrayList<>());
            });
        }
    }


//    calling onItemClick from bookmarkAdapter to navigative to mainActivity and pass value
// navigation from list word to definition Fragment -- 3
    @Override
    public void onItemClick(String wordFromBookmarkOrHistory) {
        Intent intent = new Intent(Bookmark.this, MainActivity.class);
        intent.putExtra("bookmarkQuery", wordFromBookmarkOrHistory);
        startActivity(intent);
    }

}
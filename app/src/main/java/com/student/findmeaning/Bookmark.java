package com.student.findmeaning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.student.findmeaning.Adapters.BookmarkAdapter;

import java.util.Arrays;
import java.util.List;

public class Bookmark extends AppCompatActivity {
    TextView textView_pgName;
    ImageButton backImageBtn, deleteImageBtn, actionBarDeleteAll;

//    yeha list auto add hogi from model-DB Table, add ka btn definition fragement mein hoga.
    List<String> data = Arrays.asList("Item 1", "Item 2", "Item 3");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        backImageBtn = findViewById(R.id.action_bar_back);
        deleteImageBtn = findViewById(R.id.action_bar_delete);
        actionBarDeleteAll = findViewById(R.id.action_bar_delete_all);
        textView_pgName = findViewById(R.id.page_name);
        textView_pgName.setText(R.string.bookmark);

        backImageBtn.setOnClickListener(view -> onBackPressed());

//        yeha dele ka kaam hogaa. checkbox k check krne pe identify hoga k checked items delete krne hain
        deleteImageBtn.setOnClickListener(view -> Toast.makeText(this, "Delete btn clicked", Toast.LENGTH_SHORT).show());
//       delete all mein sara data delete hoga
        actionBarDeleteAll.setOnClickListener(view -> Toast.makeText(this, "Delete All btn clicked", Toast.LENGTH_SHORT).show());

        RecyclerView recyclerView = findViewById(R.id.bookmark_recyclerView);
        BookmarkAdapter bookmarkAdapter = new BookmarkAdapter(Bookmark.this, data);
        recyclerView.setAdapter(bookmarkAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }
}
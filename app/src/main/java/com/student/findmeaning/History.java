package com.student.findmeaning;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.student.findmeaning.Adapters.BookmarkAdapter;
import com.student.findmeaning.Adapters.HistoryAdapter;

import java.util.Arrays;
import java.util.List;

public class History extends AppCompatActivity {
    TextView textView_pgName;
    ImageButton backImageBtn, deleteImageBtn, actionBarDeleteAll;

    //    yeha list auto add hogii from model-DB Table, jb user search pe click kre ga tb yeha word add hojaye ga. ya jb search successful hoga tb   List<String> data = Arrays.asList("Item 1", "Item 2", "Item 3");
    List<String> historyData = Arrays.asList("Item 1 History", "Item 2 history", "Item 3 history");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        backImageBtn = findViewById(R.id.action_bar_back);
        deleteImageBtn = findViewById(R.id.action_bar_delete);
        actionBarDeleteAll = findViewById(R.id.action_bar_delete_all);
        textView_pgName = findViewById(R.id.page_name);
        textView_pgName.setText(R.string.history);

        backImageBtn.setOnClickListener(view -> onBackPressed());

//        yeha dele ka kaam hoga. checkbox k check krne pe identify hoga k checked items delete krne hain
        deleteImageBtn.setOnClickListener(view -> Toast.makeText(this, "Delete btn clicked", Toast.LENGTH_SHORT).show());
//       delete all mein sara data delete hoga
        actionBarDeleteAll.setOnClickListener(view -> Toast.makeText(this, "Delete All btn clicked", Toast.LENGTH_SHORT).show());

        RecyclerView recyclerView = findViewById(R.id.history_recyclerView);
        HistoryAdapter historyAdapter = new HistoryAdapter(History.this,historyData);
        recyclerView.setAdapter(historyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


    }
}
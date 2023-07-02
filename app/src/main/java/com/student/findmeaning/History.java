package com.student.findmeaning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.student.findmeaning.Adapters.HistoryAdapter;
import com.student.findmeaning.Models.HistoryModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class History extends AppCompatActivity implements OnBookmarkItemClickListener{
    TextView textView_pgName;
    ImageButton backImageBtn, deleteImageBtn, actionBarDeleteAll;
    private ArrayList<HistoryModel> historyArrayList = new ArrayList<>();
    private WordDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        dbHandler = new WordDBHandler(getApplicationContext());

        backImageBtn = findViewById(R.id.action_bar_back);
        backImageBtn.setOnClickListener(view -> onBackPressed());

        textView_pgName = findViewById(R.id.page_name);
        textView_pgName.setText(R.string.history);

        RecyclerView recyclerView = findViewById(R.id.history_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        final HistoryAdapter historyAdapter = new HistoryAdapter(historyArrayList, getApplicationContext(), dbHandler);
        historyAdapter.setOnItemClickListener(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(historyAdapter);

//        displaying successful search word in list through db
        historyArrayList= new ArrayList<>();
        historyArrayList = (ArrayList<HistoryModel>) dbHandler.getAllHistory();
        Collections.reverse(historyArrayList);
        historyAdapter.setDataChangedHistory(historyArrayList);

        deleteImageBtn = findViewById(R.id.action_bar_delete_history);
        deleteImageBtn.setOnClickListener(view -> Toast.makeText(this, "Delete btn clicked", Toast.LENGTH_SHORT).show());

        actionBarDeleteAll = findViewById(R.id.action_bar_delete_all_history);
        actionBarDeleteAll.setOnClickListener(view -> Toast.makeText(this, "Delete All btn clicked", Toast.LENGTH_SHORT).show());

        if (historyArrayList.isEmpty()){
            deleteImageBtn.setVisibility(View.INVISIBLE);
            actionBarDeleteAll.setVisibility(View.INVISIBLE);
            Toast.makeText(this, R.string.empty_history, Toast.LENGTH_LONG).show();
        }else{
            actionBarDeleteAll.setVisibility(View.VISIBLE);
            actionBarDeleteAll.setOnClickListener(view -> {
                PopupMenu popupMenu = new PopupMenu(History.this, view);
                popupMenu.inflate(R.menu.del);
                popupMenu.setOnMenuItemClickListener(item -> {
                    int itemId = item.getItemId();
                    if (itemId == R.id.menu_delete_all){
                        dbHandler.deleteHistory();
                        historyArrayList.clear();
                        historyAdapter.setDataChangedHistory(historyArrayList);
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(this, R.string.history_cleared, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                });
                popupMenu.show();
            });

            deleteImageBtn.setVisibility(View.VISIBLE);
            deleteImageBtn.setOnClickListener(view -> {
                ArrayList<Integer> selectedPositions = historyAdapter.getSelectedPositions();
                ArrayList<Integer> copyPositions = new ArrayList<>(selectedPositions); // Create a copy of the selectedPositions list
                for (int position : copyPositions){
                    historyAdapter.deleteWords(position);
                }
                historyAdapter.setSelectedPositions(new ArrayList<>());
            });
        }
    }

    @Override
    public void onItemClick(String wordFromBookmarkOrHistory) {
        Intent intent = new Intent(History.this, MainActivity.class);
        intent.putExtra("historyQuery", wordFromBookmarkOrHistory);
        startActivity(intent);
    }
}
package com.student.findmeaning;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.student.findmeaning.Adapters.NotesAdapter;
import com.student.findmeaning.Models.NotesModel;

import java.util.ArrayList;
import java.util.Collections;

public class Notes extends AppCompatActivity implements OnBookmarkItemClickListener, OnDialogCloseListener{
    public TextView textView_pgName;
    public ImageButton backImageBtn, deleteImageBtn, deleteAllNotes;
    public FloatingActionButton floatingActionButton;
    public NotesDBHandler notesDBHandler;
    public NotesAdapter notesAdapter;
    public ArrayList<NotesModel> notesModelArrayList;
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        backImageBtn = findViewById(R.id.action_bar_back);
        backImageBtn.setOnClickListener(view -> super.onBackPressed());

        floatingActionButton = findViewById(R.id.floatingActionButton);
        textView_pgName = findViewById(R.id.page_name);
        textView_pgName.setText(R.string.notes);

        notesDBHandler = new NotesDBHandler(getApplicationContext());

        notesModelArrayList = new ArrayList<>();
        notesAdapter = new NotesAdapter(notesModelArrayList, notesDBHandler, getApplicationContext());
        //    for navigation from list word to definition Fragment --
        notesAdapter.setOnItemClickListener(this);

        recyclerView = findViewById(R.id.notes_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(notesAdapter);

        notesModelArrayList = (ArrayList<NotesModel>) notesDBHandler.displayAllNotes();
        Collections.reverse(notesModelArrayList);
        notesAdapter.setDataChangeNotes(notesModelArrayList);


        deleteImageBtn = findViewById(R.id.action_bar_delete_notes);
        deleteAllNotes = findViewById(R.id.action_bar_delete_all_notes);

            deleteAllNotes.setVisibility(View.VISIBLE);
            deleteAllNotes.setOnClickListener(view -> {
                PopupMenu popupMenu = new PopupMenu(Notes.this, view);
                popupMenu.inflate(R.menu.del);
                popupMenu.setOnMenuItemClickListener(item -> {
                    int itemId = item.getItemId();
                    if (itemId == R.id.menu_delete_all){
                        notesDBHandler.deleteAllNotes();
                        notesModelArrayList.clear();
                        notesAdapter.setDataChangeNotes(notesModelArrayList);

                        Toast.makeText(this, R.string.notes_cleared, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                });
                popupMenu.show();
            });

            deleteImageBtn.setVisibility(View.VISIBLE);
            deleteImageBtn.setOnClickListener(view -> {
                ArrayList<Integer> selectedPositions = notesAdapter.getSelectedPositions();
                ArrayList<Integer> copyPositions = new ArrayList<>(selectedPositions); // Create a copy of the selectedPositions list
                for (int position : copyPositions){
                    notesAdapter.deleteWords(position);
                }
                notesAdapter.setSelectedPositions(new ArrayList<>());
            });

            setOpenAddBtn(floatingActionButton);
    }

    public void setOpenAddBtn(FloatingActionButton openAddBtn) {
        this.floatingActionButton = openAddBtn;
        openAddBtn.setOnClickListener(view -> {
            BottomFragment bottomDialogFragment = new BottomFragment();
            bottomDialogFragment.setOnDialogCloseListener(this);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(bottomDialogFragment,BottomFragment.TAG).commit();

        });
    }

    @Override
    public void onItemClick(String wordFromBookmarkOrHistory) {
        Intent intent = new Intent(Notes.this, MainActivity.class);
        intent.putExtra("notesQuery", wordFromBookmarkOrHistory);
        startActivity(intent);
    }

//    to update the list when data is added or edited --
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        notesModelArrayList.clear();
        notesModelArrayList.addAll(notesDBHandler.displayAllNotes());
        Collections.reverse(notesModelArrayList);
        notesAdapter.setDataChangeNotes(notesModelArrayList);
        notesAdapter.notifyDataSetChanged();
    }
}
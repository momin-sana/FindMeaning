package com.student.findmeaning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Notes extends AppCompatActivity {
    public TextView textView_pgName;
    public ImageButton backImageBtn, deleteImageBtn, deleteAllNotes;
    public FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        backImageBtn = findViewById(R.id.action_bar_back);
        deleteImageBtn = findViewById(R.id.action_bar_delete_notes);
        deleteAllNotes = findViewById(R.id.action_bar_delete_all_notes);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        textView_pgName = findViewById(R.id.page_name);
        textView_pgName.setText(R.string.notes);

        backImageBtn.setOnClickListener(view -> onBackPressed());

        setOpenAddBtn(floatingActionButton);
    }

    public void setOpenAddBtn(FloatingActionButton openAddBtn) {
        this.floatingActionButton = openAddBtn;
        openAddBtn.setOnClickListener(view -> {
            BottomFragment bottomDialogFragment = new BottomFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down, R.anim.slide_up, R.anim.slide_down);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(android.R.id.content, bottomDialogFragment).commit();

        });
    }

}
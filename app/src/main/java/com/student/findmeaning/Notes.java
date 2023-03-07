package com.student.findmeaning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class Notes extends AppCompatActivity {
    TextView textView_pgName;
    ImageButton backImageBtn, deleteImageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        backImageBtn = findViewById(R.id.action_bar_back);
//        deleteImageBtn = view.findViewById(R.id.action_bar_delete);
        textView_pgName = findViewById(R.id.page_name);
        textView_pgName.setText(R.string.notes);
//
        backImageBtn.setOnClickListener(view -> onBackPressed());
    }
}
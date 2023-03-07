package com.student.findmeaning;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class History extends AppCompatActivity {
    ImageButton backImageBtn, deleteImageBtn;
    TextView textView_pgName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        backImageBtn = findViewById(R.id.action_bar_back);
//        deleteImageBtn = view.findViewById(R.id.action_bar_delete);
        textView_pgName = findViewById(R.id.page_name);
        textView_pgName.setText(R.string.history);
//
        backImageBtn.setOnClickListener(view -> onBackPressed());

    }
}
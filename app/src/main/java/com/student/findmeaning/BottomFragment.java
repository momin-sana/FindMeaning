package com.student.findmeaning;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomFragment extends BottomSheetDialogFragment {
    public Button enterBtn;
    public TextView textViewBottomDialog;
    public EditText editTextWordBottomDialog, editTextDescriptionBottomDialog;


    public BottomFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom, container, false);
        textViewBottomDialog = view.findViewById(R.id.textView_bottomDialog);
        editTextWordBottomDialog = view.findViewById(R.id.editText_word_bottomDialog);
        editTextDescriptionBottomDialog = view.findViewById(R.id.editText_description_bottomDialog);
        enterBtn = view.findViewById(R.id.enterButton_bottomDialog);

        return view;
    }
}
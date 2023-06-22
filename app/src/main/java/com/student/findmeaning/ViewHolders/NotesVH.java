package com.student.findmeaning.ViewHolders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.student.findmeaning.R;

public class NotesVH extends RecyclerView.ViewHolder {
    public TextView wordTvNotes, descriptionTvNotes;
    public ImageButton editBtn;
    public CheckBox checkBoxDel;

    public NotesVH(@NonNull View itemView) {
        super(itemView);
        wordTvNotes = itemView.findViewById(R.id.notes_word_textView);
        descriptionTvNotes = itemView.findViewById(R.id.notes_description_textView);
        editBtn = itemView.findViewById(R.id.notes_edit);
        checkBoxDel = itemView.findViewById(R.id.notes_checkBox);
    }
}

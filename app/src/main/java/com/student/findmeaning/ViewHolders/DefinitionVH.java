package com.student.findmeaning.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.student.findmeaning.R;

public class DefinitionVH extends RecyclerView.ViewHolder {
    public TextView definition_text, example_text, synonyms_textView, antonyms_textView;

    public DefinitionVH(@NonNull View itemView) {
        super(itemView);
        definition_text = itemView.findViewById(R.id.definition_text);
        example_text = itemView.findViewById(R.id.example_text);
//        synonyms_textView = itemView.findViewById(R.id.synonyms_textView);
//        antonyms_textView = itemView.findViewById(R.id.antonyms_textView);
    }
}

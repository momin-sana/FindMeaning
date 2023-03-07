package com.student.findmeaning.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.student.findmeaning.R;

public class MeaningVH extends RecyclerView.ViewHolder {
    public TextView partsOfSpeech_text;
    public RecyclerView definition_recyclerView;

    public MeaningVH(@NonNull View itemView) {
        super(itemView);
        partsOfSpeech_text = itemView.findViewById(R.id.partsOfSpeech_text);
        definition_recyclerView = itemView.findViewById(R.id.definition_recyclerView);
    }
}

package com.student.findmeaning.ViewHolders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.student.findmeaning.R;

public class PhoneticVH extends RecyclerView.ViewHolder {
    public TextView phonetic_text;
    public ImageButton phonetic_audio, phonetic_audio_invisible;

    public PhoneticVH(@NonNull View itemView) {
        super(itemView);

        phonetic_text = itemView.findViewById(R.id.phonetic_text);
        phonetic_audio = itemView.findViewById(R.id.phonetic_audio);
        phonetic_audio_invisible = itemView.findViewById(R.id.phonetic_audio_invisible);
    }
}

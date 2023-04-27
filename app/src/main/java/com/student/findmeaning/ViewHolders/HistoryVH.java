package com.student.findmeaning.ViewHolders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.student.findmeaning.R;

public class HistoryVH extends RecyclerView.ViewHolder{
    public TextView historyTVList;
    public CheckBox historyCheckBox;
    public HistoryVH(@NonNull View itemView) {
        super(itemView);
        historyCheckBox = itemView.findViewById(R.id.historyCheckBox);
        historyTVList = itemView.findViewById(R.id.historyList_item_textView);

    }
}

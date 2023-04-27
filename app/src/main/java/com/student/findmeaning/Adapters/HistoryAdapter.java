package com.student.findmeaning.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.student.findmeaning.R;
import com.student.findmeaning.ViewHolders.BookmarkVH;
import com.student.findmeaning.ViewHolders.HistoryVH;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryVH> {
    private final List<String> historyListData;
    private Context context;

    public HistoryAdapter( Context context, List<String> historyListData) {
        this.historyListData = historyListData;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryVH holder, int position) {
        String item = historyListData.get(position);
        holder.historyTVList.setText(item);
        holder.historyCheckBox.setChecked(false);
        holder.historyTVList.setOnClickListener(view -> {
//                When user clicks on text it should open definitionfragment with its meaning. jo kaam searchview k click krne pe hoga wahi kaam yeha text k click krne pe hoga
            Toast.makeText(context, "clicked on: "+ item, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return historyListData.size();
    }
}

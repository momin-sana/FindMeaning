package com.student.findmeaning.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.student.findmeaning.Models.BookmarkModel;
import com.student.findmeaning.Models.History;
import com.student.findmeaning.Models.HistoryModel;
import com.student.findmeaning.R;
import com.student.findmeaning.ViewHolders.HistoryVH;
import com.student.findmeaning.WordDBHandler;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryVH> {
    private ArrayList<HistoryModel> historyListData;
    private final Context context;
    private WordDBHandler dbHandler;

    public HistoryAdapter(ArrayList<HistoryModel> historyListData, Context context, WordDBHandler dbHandler) {
        this.historyListData = historyListData;
        this.context = context;
        this.dbHandler = dbHandler;
    }

    @NonNull
    @Override
    public HistoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryVH holder, int position) {
        HistoryModel item = historyListData.get(position);
        holder.historyTVList.setText(item.getWord().toLowerCase());
        holder.itemView.setTag(item.getId());

        holder.historyCheckBox.setChecked(false);
        holder.historyTVList.setOnClickListener(view -> {
//                When user clicks on text it should open definition fragment with its meaning.
//                jo kaam searchview k click krne pe hoga wahi kaam yeha text k click krne pe hoga
            Toast.makeText(context, "clicked on: "+ item, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        if (historyListData == null) {
            return 0;
        } else {
            return historyListData.size();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataChangedHistory(ArrayList<HistoryModel> historyListData){
        dbHandler = new WordDBHandler(context);
        dbHandler.getAllHistory();
        this.historyListData = historyListData;
        notifyDataSetChanged();
    }
}

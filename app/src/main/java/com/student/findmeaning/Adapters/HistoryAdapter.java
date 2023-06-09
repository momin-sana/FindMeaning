package com.student.findmeaning.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.student.findmeaning.Models.BookmarkModel;
import com.student.findmeaning.Models.HistoryModel;
import com.student.findmeaning.OnBookmarkItemClickListener;
import com.student.findmeaning.R;
import com.student.findmeaning.ViewHolders.HistoryVH;
import com.student.findmeaning.WordDBHandler;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryVH> {
    private ArrayList<HistoryModel> historyListData;
    private final Context context;
    private WordDBHandler dbHandler;
    public OnBookmarkItemClickListener clickListener;
    private ArrayList<Integer> selectedPositions = new ArrayList<>();
    private final ArrayList<HistoryModel> selectList=new ArrayList<>();


    public HistoryAdapter(ArrayList<HistoryModel> historyListData, Context context, WordDBHandler dbHandler) {
        this.historyListData = historyListData;
        this.context = context;
        this.dbHandler = dbHandler;
    }

    public void setOnItemClickListener(OnBookmarkItemClickListener listener) {
        this.clickListener = listener;
    }

    public ArrayList<Integer> getSelectedPositions() {
        return selectedPositions;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSelectedPositions(ArrayList<Integer> selectedPositions) {
        this.selectedPositions = selectedPositions;
        notifyDataSetChanged();
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

        holder.historyTVList.setOnClickListener(view -> {
            String word = item.getWord();
            if (clickListener != null){
                clickListener.onItemClick(word);
            }
        });

        holder.historyCheckBox.setChecked(selectedPositions.contains(position));
        holder.historyCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                selectedPositions.add(position);
                selectList.add(item);
            }else{
                selectedPositions.remove(Integer.valueOf(position));
                selectList.remove(item);
            }
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


    @SuppressLint("NotifyDataSetChanged")
    public void deleteWords(int position) {
        ArrayList<Integer> ids = new ArrayList<>();
        for (HistoryModel historyModel : selectList){
            ids.add(historyModel.getId());
        }
        for (Integer id : ids){
            dbHandler.deleteWord(id);
        }
        historyListData.removeAll(selectList);
        notifyDataSetChanged();
        selectList.clear();
        selectedPositions.clear();
    }
}

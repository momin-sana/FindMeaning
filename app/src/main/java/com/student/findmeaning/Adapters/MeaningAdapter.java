package com.student.findmeaning.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.student.findmeaning.Models.Meaning;
import com.student.findmeaning.R;
import com.student.findmeaning.ViewHolders.MeaningVH;

import java.util.List;

public class MeaningAdapter extends RecyclerView.Adapter<MeaningVH> {
    private final Context context;
    private List<Meaning> meaningList;

    public MeaningAdapter(Context context, List<Meaning> meaningList) {
        this.context = context;
        this.meaningList = meaningList;
    }

    @NonNull
    @Override
    public MeaningVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MeaningVH(LayoutInflater.from(context).inflate(R.layout.meaning_list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MeaningVH holder, int position) {
        holder.partsOfSpeech_text.setText(meaningList.get(position).getPartOfSpeech());
        //        add data for definition recyclerview
        holder.definition_recyclerView.setHasFixedSize(true);
        holder.definition_recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
//        passing definition adapter here
        DefinitionAdapter definitionAdapter = new DefinitionAdapter(context, meaningList.get(position).getDefinitions());
        holder.definition_recyclerView.setAdapter(definitionAdapter);
    }

    @Override
    public int getItemCount() {
        if (meaningList == null) {
            return 0;
        }
        return meaningList.size();
    }

    public void setData(List<Meaning> meaningList) {
        this.meaningList = meaningList;
        notifyDataSetChanged();
    }
}

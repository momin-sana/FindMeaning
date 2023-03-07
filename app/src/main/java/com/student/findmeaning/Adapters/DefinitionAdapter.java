package com.student.findmeaning.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.student.findmeaning.Models.Definition;
import com.student.findmeaning.R;
import com.student.findmeaning.ViewHolders.DefinitionVH;

import java.util.List;

public class DefinitionAdapter extends RecyclerView.Adapter<DefinitionVH> {
    public Context context;
    public List<Definition> definitionList;

    public DefinitionAdapter(Context context, List<Definition> definitionList) {
        this.context = context;
        this.definitionList = definitionList;
    }

    @NonNull
    @Override
    public DefinitionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DefinitionVH(LayoutInflater.from(context).inflate(R.layout.definition_list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DefinitionVH holder, int position) {
        holder.definition_text.setText(definitionList.get(position).getDefinition());
        holder.example_text.setText(definitionList.get(position).getDefinition());
//        to add synonyms and antonyms, as they both are in list must be linked with StringBuilder
        StringBuilder synonyms = new StringBuilder();
        StringBuilder antonyms = new StringBuilder();
        synonyms.append(definitionList.get(position).getSynonyms());
        antonyms.append(definitionList.get(position).getAntonyms());

        holder.synonyms_textView.setText(synonyms);
        holder.antonyms_textView.setText(antonyms);

//        make text selected
        holder.synonyms_textView.setSelected(true);
        holder.antonyms_textView.setSelected(true);

    }

    @Override
    public int getItemCount() {

        if (definitionList == null) {
            return 0;
        }
        return definitionList.size();
    }

    public void setData(List<Definition> definitionList) {
        this.definitionList = definitionList;
        notifyDataSetChanged();
    }
}

package com.student.findmeaning.Adapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.student.findmeaning.R;
import com.student.findmeaning.ViewHolders.BookmarkVH;


import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkVH> {
    private final List<String> bookmarkListData;
    private Context context;

    public BookmarkAdapter(Context context, List<String> data){
        this.bookmarkListData = data;
        this.context = context;
    }

    @NonNull
    @Override
    public BookmarkVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookmarkVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkVH holder, int position) {
        String item = bookmarkListData.get(position);
        holder.bookmarkTVList.setText(item);
        holder.bookmarkCheckBox.setChecked(false);
        holder.bookmarkTVList.setOnClickListener(view -> {
//                When user clicks on text it should open definitionfragment with its meaning. jo kaam searchview k click krne pe hoga wahi kaam yeha text k click krne pe hoga
            Toast.makeText(context, "clicked on: "+ item, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return bookmarkListData.size();
    }
}

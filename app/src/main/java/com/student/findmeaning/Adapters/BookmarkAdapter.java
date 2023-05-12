package com.student.findmeaning.Adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.student.findmeaning.Models.BookmarkModel;
import com.student.findmeaning.R;
import com.student.findmeaning.ViewHolders.BookmarkVH;
import com.student.findmeaning.WordDBHandler;


import java.util.ArrayList;
import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkVH> {
    private List<BookmarkModel> bookmarkListData;
    private Context context;
    private final WordDBHandler dbHandler;

    public BookmarkAdapter(List<BookmarkModel> bookmarkListData, Context context, WordDBHandler dbHandler) {
        this.bookmarkListData = bookmarkListData;
        this.context = context;
        this.dbHandler = dbHandler;
    }

    @NonNull
    @Override
    public BookmarkVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookmarkVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkVH holder, int position) {
        BookmarkModel bookmarkModel = bookmarkListData.get(position);
        holder.bookmarkTVList.setText(bookmarkModel.getWord());
        holder.itemView.setTag(bookmarkModel.getId());

//        deleteWord(position, bookmarkModel.getId());

        holder.bookmarkCheckBox.setChecked(false);   // add delete single or multi fuction here, onCheck feature

        //        yeha dele ka kaam hogaa. checkbox k check krne pe identify hoga k checked items delete krne hain
//        holder.deleteImageBtn.setOnClickListener(view -> //yeh and checkbox link hoga is btn k click krne pe checkbox ka option activate hoga/ ya visible hoga k select kro kon kon se word del krne hain
//                Toast.makeText(context, "Delete btn clicked", Toast.LENGTH_SHORT).show());
//
//        //       delete all mein sara data delete hoga
//        holder.actionBarDeleteAll.setOnClickListener(view ->
//                Toast.makeText(context, "Delete All btn clicked", Toast.LENGTH_SHORT).show());

        holder.bookmarkTVList.setOnClickListener(view -> {
//                When user clicks on text it should open definition fragment with its meaning.
//                jo kaam searchview k click krne pe hoga wahi kaam yeha text k click krne pe hoga

            Toast.makeText(context, "clicked on: "+ bookmarkModel, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        if (bookmarkListData == null) {
            return 0;
        } else {
            return bookmarkListData.size();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataChangedBookmark(ArrayList<BookmarkModel> bookmarkListData){
       dbHandler.getAllBookmarks();
        this.bookmarkListData = bookmarkListData;
        notifyDataSetChanged();
    }

    public void deleteWord(int position, int wordId){
        dbHandler.deleteWord(wordId);
        bookmarkListData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, bookmarkListData.size());
    }

    public int getPosition(String word) {
        for (int i = 0; i < bookmarkListData.size(); i++) {
            BookmarkModel bookmark = bookmarkListData.get(i);
            if (bookmark.getWord().equals(word)) {
                return i;
            }
        }
        return -1;
    }
}

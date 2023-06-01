package com.student.findmeaning.Adapters;


import android.annotation.SuppressLint;
import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.student.findmeaning.Models.BookmarkModel;
import com.student.findmeaning.OnBookmarkItemClickListener;
import com.student.findmeaning.R;
import com.student.findmeaning.ViewHolders.BookmarkVH;
import com.student.findmeaning.WordDBHandler;


import java.util.ArrayList;
import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkVH> {
    private List<BookmarkModel> bookmarkListData;
    private Context context;
    private TextView tvEmpty;
    private WordDBHandler dbHandler;
    public BookmarkModel bookmarkModel;
    private ArrayList<BookmarkModel> selectList=new ArrayList<>();
    public OnBookmarkItemClickListener clickListener;


    public BookmarkAdapter(List<BookmarkModel> bookmarkListData, Context context, WordDBHandler dbHandler) {
        this.bookmarkListData = bookmarkListData;
        this.context = context;
        this.dbHandler = dbHandler;
    }
    public BookmarkAdapter(List<BookmarkModel> bookmarkListData, Context context, TextView tvEmpty, WordDBHandler dbHandler) {
        this.bookmarkListData = bookmarkListData;
        this.context = context;
        this.tvEmpty = tvEmpty;
        this.dbHandler = dbHandler;
    }

    public void setOnItemClickListener(OnBookmarkItemClickListener listener) {
        this.clickListener = listener;
    }


    @NonNull
    @Override
    public BookmarkVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookmarkVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkVH holder, int position) {
        BookmarkModel clickedItem = bookmarkListData.get(position);

        holder.bookmarkTVList.setText(clickedItem.getWord());
        holder.bookmarkCheckBox.setChecked(selectList.contains(bookmarkModel));   // add delete single or multi fuction here, onCheck feature
        holder.itemView.setTag(clickedItem.getId());

        holder.bookmarkCheckBox.setOnClickListener(view -> {
            if (holder.bookmarkCheckBox.isChecked()){
                holder.bookmarkCheckBox.setChecked(true);
                selectList.add(bookmarkModel);
            }else {
                selectList.remove(bookmarkModel);
            }
        });

        //        yeha dele ka kaam hogaa. checkbox k check krne pe identify hoga k checked items delete krne hain
//        holder.deleteImageBtn.setOnClickListener(view -> //yeh and checkbox link hoga is btn k click krne pe checkbox ka option activate hoga/ ya visible hoga k select kro kon kon se word del krne hain
//                Toast.makeText(context, "Delete btn clicked", Toast.LENGTH_SHORT).show());
//
//        //       delete all mein sara data delete hoga
//        holder.actionBarDeleteAll.setOnClickListener(view ->
//                Toast.makeText(context, "Delete All btn clicked", Toast.LENGTH_SHORT).show());

        holder.bookmarkTVList.setOnClickListener(view -> {
               String word = clickedItem.getWord();
                if (clickListener != null){
                    clickListener.onItemClick(word);
                }
        });
    }

//    private void ClickItem(BookmarkVH holder) {
//        BookmarkModel s = bookmarkListData.get(holder.getAdapterPosition());
//        if (holder.bookmarkCheckBox.getVisibility() == View.GONE){
//            holder.bookmarkCheckBox.setVisibility(View.VISIBLE);
//            holder.itemView.setBackgroundColor(Color.LTGRAY);
//            selectList.add(s);
//        }else{
//            holder.bookmarkCheckBox.setVisibility(View.GONE);
//            selectList.remove(s);
//        }
//    }

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
        dbHandler = new WordDBHandler(context);
       dbHandler.getAllBookmarks();
        this.bookmarkListData = bookmarkListData;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void deleteWords(int position){
        ArrayList<Integer> ids = new ArrayList<>();
        for (BookmarkModel bookmarkModel : selectList){
            ids.add(bookmarkModel.getId());
        }
        dbHandler.deleteWord(position);
        bookmarkListData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, bookmarkListData.size());
        notifyDataSetChanged();
        selectList.clear();
    }

}

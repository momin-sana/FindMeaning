package com.student.findmeaning.Adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
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
    private final Context context;
    private WordDBHandler dbHandler;
    private ArrayList<Integer> selectedPositions = new ArrayList<>();
    private final ArrayList<BookmarkModel> selectList=new ArrayList<>();
    public OnBookmarkItemClickListener clickListener;


    public BookmarkAdapter(List<BookmarkModel> bookmarkListData, Context context, WordDBHandler dbHandler) {
        this.bookmarkListData = bookmarkListData;
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
    public BookmarkVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookmarkVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkVH holder, int position) {
        BookmarkModel clickedItem = bookmarkListData.get(position);
        String clickedWord = clickedItem.getWord();
        String text = clickedWord.toLowerCase();
        holder.bookmarkTVList.setText(text);
        holder.itemView.setTag(clickedItem.getId());

        //         navigation from list word to definition Fragment -- 2
        holder.bookmarkTVList.setOnClickListener(view -> {
            String word = clickedItem.getWord();
            if (clickListener != null){
//                    onclick from interface Class
                clickListener.onItemClick(word);
            }
        });


        holder.bookmarkCheckBox.setChecked(selectedPositions.contains(position));
        holder.bookmarkCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                selectedPositions.add(position);
                selectList.add(clickedItem);
            }else{
                selectedPositions.remove(Integer.valueOf(position));
                selectList.remove(clickedItem);
            }
            if (selectedPositions.size() > 0){

            }
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
        for (Integer id : ids) {
            dbHandler.deleteWord(id);
        }
        bookmarkListData.removeAll(selectList);
        notifyDataSetChanged();
        selectList.clear();
        selectedPositions.clear();
    }

}

package com.student.findmeaning.ViewHolders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.student.findmeaning.R;


public class BookmarkVH extends RecyclerView.ViewHolder {
        public TextView bookmarkTVList;
        public CheckBox bookmarkCheckBox;

    public BookmarkVH(@NonNull View itemView) {
        super(itemView);
            bookmarkCheckBox = itemView.findViewById(R.id.bookmarkCheckBox);
            bookmarkTVList = itemView.findViewById(R.id.bookmarkList_item_textView);
    }
}

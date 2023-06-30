package com.student.findmeaning.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.student.findmeaning.BottomFragment;
import com.student.findmeaning.Models.NotesModel;
import com.student.findmeaning.NotesDBHandler;
import com.student.findmeaning.OnBookmarkItemClickListener;
import com.student.findmeaning.R;
import com.student.findmeaning.ViewHolders.NotesVH;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesVH> {
    private ArrayList<NotesModel> notesModelArrayList;
    private NotesDBHandler notesDBHandler;
    public Context context;
    Bundle bundle;
    public OnBookmarkItemClickListener clickListener;
    private ArrayList<Integer> selectedPositions = new ArrayList<>();
    private final ArrayList<NotesModel> selectList=new ArrayList<>();



    public NotesAdapter(ArrayList<NotesModel> notesModelArrayList, NotesDBHandler notesDBHandler, Context context) {
        this.notesModelArrayList = notesModelArrayList;
        this.notesDBHandler = notesDBHandler;
        this.context = context;
    }

    //    for navigation from list word to definition Fragment -- 2
    public void setOnItemClickListener(OnBookmarkItemClickListener listener) {
        this.clickListener = listener;
    }

    public ArrayList<Integer> getSelectedPositions() {
        return selectedPositions;
    }
    //set a new list of selected positions and notify the adapter of the data set change
    @SuppressLint("NotifyDataSetChanged")
    public void setSelectedPositions(ArrayList<Integer> selectedPositions) {
        this.selectedPositions = selectedPositions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesVH holder, int position) {
        NotesModel clickedItem = notesModelArrayList.get(position);
        String word = clickedItem.getWord();
        String note = clickedItem.getNote();
        holder.itemView.setTag(clickedItem.getId());

        holder.wordTvNotes.setText(word);
        holder.wordTvNotes.setOnClickListener(view -> {
            //         navigation from list word to definition Fragment -- 2
            if (clickListener != null){
//                    onclick from interface Class
                clickListener.onItemClick(word);
            }
        });

        holder.descriptionTvNotes.setText(note);

        holder.checkBoxDel.setChecked(selectedPositions.contains(position));
        holder.checkBoxDel.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                selectedPositions.add(position);
                selectList.add(clickedItem);
            }else{
                selectedPositions.remove(Integer.valueOf(position));
                selectList.remove(clickedItem);
            }
        });

        holder.editBtn.setOnClickListener(view -> {
            bundle = new Bundle();
            bundle.putInt("id", notesModelArrayList.get(position).getId());
            bundle.putString("word", notesModelArrayList.get(position).getWord());
            bundle.putString("note", notesModelArrayList.get(position).getNote());

            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            Fragment bottomDialogFragment = new BottomFragment();
            bottomDialogFragment.setArguments(bundle);
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .add(bottomDialogFragment, BottomFragment.TAG)
                    .addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount() {
        if (notesModelArrayList == null) {
            return 0;
        } else {
            return notesModelArrayList.size();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataChangeNotes(ArrayList<NotesModel> notesModelArrayList) {
        notesDBHandler = new NotesDBHandler(context);
        this.notesModelArrayList = notesModelArrayList;
        notesDBHandler.displayAllNotes();
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void deleteWords(int position){
        ArrayList<Integer> ids = new ArrayList<>();
        for (NotesModel notesModel : selectList){
            ids.add(notesModel.getId());
        }
        for (Integer id : ids) {
            notesDBHandler.deleteNoteById(id);
        }
        notesModelArrayList.removeAll(selectList);
        notifyDataSetChanged();
        selectList.clear();
        selectedPositions.clear();
    }

}

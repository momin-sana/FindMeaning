package com.student.findmeaning;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.student.findmeaning.Adapters.NotesAdapter;
import com.student.findmeaning.Models.NotesModel;

import java.util.ArrayList;

public class BottomFragment extends BottomSheetDialogFragment implements OnDialogCloseListener {
    public Button enterBtn;
    public TextView textViewBottomDialog;
    public EditText editTextWordBottomDialog, editTextDescriptionBottomDialog;
    public OnDialogCloseListener onDialogCloseListener;
    public static final String TAG = "BottomDialog";
    private NotesDBHandler notesDBHandler;
    public NotesModel notesModel;
    String word, note;
    int id;
    public NotesAdapter notesAdapter;
    public ArrayList<NotesModel> notesModelArrayList = new ArrayList<>();

    public BottomFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom, container, false);

        textViewBottomDialog = view.findViewById(R.id.textView_bottomDialog);
        editTextWordBottomDialog = view.findViewById(R.id.editText_word_bottomDialog);
        editTextDescriptionBottomDialog = view.findViewById(R.id.editText_description_bottomDialog);
        enterBtn = view.findViewById(R.id.enterButton_bottomDialog);

        notesDBHandler = new NotesDBHandler(getActivity());
        notesAdapter = new NotesAdapter(notesModelArrayList, notesDBHandler, getActivity());

//      working with single bottom fragment for 2 purpose : add and edit
        boolean isEdit = false;

        if (getArguments() != null){
            isEdit = true;
            textViewBottomDialog.setText(R.string.edit_dialog);
            id = getArguments().getInt("id");
            Log.d(TAG, "id: "+ id + "word: "+ word);
            word = getArguments().getString("word");
            note = getArguments().getString("note");
            editTextWordBottomDialog.setText(word);
            editTextDescriptionBottomDialog.setText(note);
        }
        else textViewBottomDialog.setText(R.string.add_new_note);

//      update operation
        final boolean finalIsEdit = isEdit;

        enterBtn.setOnClickListener(view1 -> {
            String wordBDF = editTextWordBottomDialog.getText().toString().trim();
            String descriptionWordBDF = editTextDescriptionBottomDialog.getText().toString();

            if(finalIsEdit){

                notesDBHandler = new NotesDBHandler(this.getActivity());
                notesModel = new NotesModel();
                notesModel.setId(id);
                notesModel.setWord(wordBDF);
                notesModel.setNote(descriptionWordBDF);
                //    to update the list when data is edited --
                notesDBHandler.updateNote(notesModel, (OnDialogCloseListener) getActivity());
                dismiss();

            }else if (TextUtils.isEmpty(wordBDF) && TextUtils.isEmpty(descriptionWordBDF)){
                editTextWordBottomDialog.setError(getString(R.string.enter_word));
                editTextDescriptionBottomDialog.setError(getString(R.string.enter_description));
                Toast.makeText(getActivity(), R.string.note_not_inserted, Toast.LENGTH_SHORT).show();
                return;
            }else if (wordBDF.length() > 1){
                notesDBHandler.addWordNotes(wordBDF, descriptionWordBDF);
                editTextWordBottomDialog.setText("");
                editTextDescriptionBottomDialog.setText("");
            }
            //    to update the list when data is added --
            if (onDialogCloseListener != null) {
                onDialogCloseListener.onDialogClose(null); // Notify the listener
            }
            dismiss();
        });


        return view;
    }


    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        FragmentActivity fragmentActivity = this.getActivity();
        if (fragmentActivity instanceof  OnDialogCloseListener){
            (onDialogCloseListener).onDialogClose(dialogInterface);
        }
    }

    public void setOnDialogCloseListener(OnDialogCloseListener onDialogCloseListener) {
        this.onDialogCloseListener = onDialogCloseListener;
    }
}
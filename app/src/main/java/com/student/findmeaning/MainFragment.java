package com.student.findmeaning;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.transition.TransitionInflater;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainFragment extends Fragment {
    TextView notes, bookmark, history, exit;
    ImageView notesIcon, bookmarkIcon, historyIcon, exitIcon;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                View customLayout = LayoutInflater.from(requireActivity()).inflate(R.layout.custom_alert_dialog, null);
                Button positiveButton = customLayout.findViewById(R.id.positive_button);
                Button negativeButton = customLayout.findViewById(R.id.negative_button);

                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setView(customLayout);
                AlertDialog dialog = builder.create();
                dialog.show();

                positiveButton.setOnClickListener(v -> {
                    TransitionInflater inflater1 = TransitionInflater.from(requireActivity());
                    setExitTransition(inflater1.inflateTransition(R.transition.fade));
                    System.exit(0);
                });
                negativeButton.setOnClickListener(v -> dialog.dismiss());
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        notes = view.findViewById(R.id.notes_textLink);
        notesIcon = view.findViewById(R.id.notes_icon);
        bookmark = view.findViewById(R.id.bookmark_textLink);
        bookmarkIcon = view.findViewById(R.id.bookmark_icon);
        history = view.findViewById(R.id.history_textLink);
        historyIcon = view.findViewById(R.id.history_icon);
        exit = view.findViewById(R.id.exit_textLink);
        exitIcon = view.findViewById(R.id.exit_icon);

        notes.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), Notes.class);
            startActivity(intent);
        });

        bookmark.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), Bookmark.class);
            startActivity(intent);
        });

        history.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), History.class);
            startActivity(intent);
        });

//        exit.setOnClickListener(view12 -> new AlertDialog.Builder(requireContext())
//                .setTitle("Exit App")
//                .setPositiveButton("Yes", (dialogInterface, i) -> {
//                    TransitionInflater inflater1 = TransitionInflater.from(requireActivity());
//                    setExitTransition(inflater1.inflateTransition(R.transition.fade));
//                    System.exit(0);
//                })
//                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss())
//                .show()
//        );

        exit.setOnClickListener(view12 -> {
            View customLayout = LayoutInflater.from(requireActivity()).inflate(R.layout.custom_alert_dialog, null);
            Button positiveButton = customLayout.findViewById(R.id.positive_button);
            Button negativeButton = customLayout.findViewById(R.id.negative_button);

            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setView(customLayout);
            AlertDialog dialog = builder.create();
            dialog.show();

            positiveButton.setOnClickListener(v -> {
                TransitionInflater inflater1 = TransitionInflater.from(requireActivity());
                setExitTransition(inflater1.inflateTransition(R.transition.fade));
                System.exit(0);
            });
            negativeButton.setOnClickListener(v -> dialog.dismiss());

            }
        );
        return view;
    }


}
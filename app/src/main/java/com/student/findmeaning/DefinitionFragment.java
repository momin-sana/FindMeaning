package com.student.findmeaning;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.ColorKt;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.drawable.ColorDrawableKt;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.appbar.AppBarLayout;
import com.student.findmeaning.Adapters.MeaningAdapter;
import com.student.findmeaning.Adapters.PhoneticAdapter;
import com.student.findmeaning.Models.DictionaryApiResponse;
import com.student.findmeaning.Models.Meaning;
import com.student.findmeaning.Models.Phonetic;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DefinitionFragment extends Fragment {

    private RecyclerView phonetic_recyclerView, meaning_recyclerView;
    private PhoneticAdapter phoneticAdapter;
    private MeaningAdapter meaningAdapter;
    private List<Phonetic> phoneticList;
    private List<Meaning> meaningList;
    private TextView word_text, meaning;
    private OnFetchDataListener onFetchDataListener;
    private LottieAnimationView progressBar;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private ImageButton phoneticBookmarkIcon;


    public DefinitionFragment() {
        // Required empty public constructor
    }

    public static  DefinitionFragment newInstance(){return new DefinitionFragment();}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFetchDataListener){
            onFetchDataListener = (OnFetchDataListener) context;
        }
        else {
            throw new RuntimeException(context
                    + " must implement OnfetchDataListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_definition, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        meaning = view.findViewById(R.id.meaning);
        phoneticBookmarkIcon = view.findViewById(R.id.phonetic_bookmark_icon);
        phoneticBookmarkIcon.setOnClickListener(view1 -> {
            phoneticBookmarkIcon.setColorFilter(ResourcesCompat.getColor(getResources(), R.color.text_icon, null));

            Bundle bundle = getArguments();
            if (bundle != null) {
                String word = bundle.getString("query");
                Toast.makeText(getActivity(), word + " is added to Bookmarks!", Toast.LENGTH_SHORT).show();
            }
        });

        phoneticList = new ArrayList<>();
        meaningList = new ArrayList<>();

        phonetic_recyclerView = view.findViewById(R.id.phonetic_recyclerView);
        phonetic_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        phoneticAdapter = new PhoneticAdapter(getContext(),phoneticList);
        phonetic_recyclerView.setAdapter(phoneticAdapter);

        meaning_recyclerView = view.findViewById(R.id.meaning_recyclerView);
        meaning_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        meaningAdapter = new MeaningAdapter(getContext(), meaningList);
        meaning_recyclerView.setAdapter(meaningAdapter);

        // view. will find the AppBarLayout inside the inflated View for the fragment,
        // while getActivity(). will find the AppBarLayout inside the activity layout.
        //If you want to use the AppBarLayout defined in the activity layout, you should use getActivity().findViewById(R.id.app_bar_layout) instead.
        appBarLayout = getActivity().findViewById(R.id.app_bar_layout);
        toolbar = getActivity().findViewById(R.id.toolbar);
        meaning_recyclerView.addOnScrollListener(scrollListener);

        return view;
    }

    public void setData(List<Phonetic> phoneticList, List<Meaning> meaningList){
        this.phoneticList = phoneticList;
        this.meaningList = meaningList;
        phoneticAdapter.setData(this.phoneticList);
        meaningAdapter.setData(this.meaningList);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        word_text = view.findViewById(R.id.word_text);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String word = bundle.getString("query");
            word_text.setText(word);
        }

        // add  back button press.
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((view1, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP){
                requireActivity().onBackPressed();
                return true;
            }
            return false;
        });
    }

    private final RecyclerView.OnScrollListener scrollListener = (new RecyclerView.OnScrollListener() {
        int scrollDist = 0;
        private static final int SHOW_THRESHOLD = 20;

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            // Calculate the scroll distance
            scrollDist += dy;
            // Check if the user has scrolled enough to hide/show the toolbar
            if (appBarLayout.getVisibility() == View.VISIBLE && scrollDist > SHOW_THRESHOLD) {
                // Hide the toolbar
                animateToolbar(false);
                appBarLayout.setVisibility(View.GONE);
                phonetic_recyclerView.setVisibility(View.GONE);
                scrollDist = 0;
            } else if (scrollDist < SHOW_THRESHOLD && appBarLayout.getVisibility() != View.VISIBLE) {
                // Show the toolbar
                animateToolbar(true);
                appBarLayout.setVisibility(View.VISIBLE);
                phonetic_recyclerView.setVisibility(View.VISIBLE);
                scrollDist = 0;
            }
        }
    });
// Method to animate the toolbar hide/show
    private void animateToolbar(boolean show) {
        if (show) {
            appBarLayout.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(2))
                    .start();
        } else {
            int toolbarHeight = toolbar.getHeight();
            appBarLayout.animate()
                    .translationY(-toolbarHeight)
                    .setInterpolator(new AccelerateInterpolator(2))
                    .start();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

   public void  fetchWordData(String word){
        if (word_text != null){
            String input = word_text.getText().toString().trim();
            if (!(input.length() <= 1)){
                if (input.matches(".*\\d.*")){
                    Toast.makeText(getActivity(), R.string.no_numbers, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        showProgressBar(true);

        Call<List<DictionaryApiResponse>> call = RetrofitClientManager.getInstance().getApi().getWordDefinition(word);
        call.enqueue(new Callback<List<DictionaryApiResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<DictionaryApiResponse>> call, @NonNull Response<List<DictionaryApiResponse>> response) {
                showProgressBar(false);
                meaning.setVisibility(View.VISIBLE);

                if (response.body() != null && response.isSuccessful()){
                    List<DictionaryApiResponse> dictionaryApiResponseList = response.body();
                    onFetchDataListener.onFetchData(dictionaryApiResponseList.get(0), word);
                }else {
                    AlertDialog alertDialog = new AlertDialog.Builder(requireContext())
                            .setTitle("Unable to fetch data from API")
                                    .setMessage("Type some other word.")
                            .setCancelable(false)
                                            .setPositiveButton("Ok", (dialogInterface, i) -> {
                                                MainFragment mainFragment = new MainFragment();
                                                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                                fragmentManager.popBackStack();
                                                fragmentManager.beginTransaction()
                                                        .replace(R.id.fragment_container, mainFragment)
                                                        .commit();
                                            })
                                                    .create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                    onFetchDataListener.onError("Unable to fetch data from API");
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<DictionaryApiResponse>> call, @NonNull Throwable t) {
                showProgressBar(false);
                onFetchDataListener.onError(t.getMessage());
            }
        });
    }

    private void showProgressBar(boolean show) {
        if (progressBar != null) {
            if (show) {
            progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        }
    }

   @Override
   public void onDestroy() {
        super.onDestroy();
// Remove the OnScrollListener from the RecyclerView
       meaning_recyclerView.removeOnScrollListener(scrollListener);
   }
}

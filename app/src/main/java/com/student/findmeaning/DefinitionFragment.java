package com.student.findmeaning;

import static androidx.fragment.app.FragmentManager.TAG;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    private List<DictionaryApiResponse> dictionaryApiResponseList;
    private List<Phonetic> phoneticList;
    private List<Meaning> meaningList;

    private ImageButton phonetic_audio;
    private TextView word_text, meaning, appnameTV, phonetic_text;
    private OnFetchDataListener onFetchDataListener;
    private ProgressBar progressBar;


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
//        else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnfetchDataListener");
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_definition, container, false);

        appnameTV = view.findViewById(R.id.appNameTV);
        progressBar = view.findViewById(R.id.progressBar);
        meaning = view.findViewById(R.id.meaning);

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
;
        word_text = view.findViewById(R.id.word_text);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String word = bundle.getString("query");
            word_text.setText(word);
        }

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((view1, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP){
                ((MainActivity) requireActivity()).onBackPressed();
                return true;
            }
            return false;
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

   public void  fetchWordData(String word){
        if (word_text != null){
            String input = word_text.getText().toString().trim();
            if (input.matches(".*\\d.*")){
                Toast.makeText(getContext(), "Word should not contain numbers", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Call<List<DictionaryApiResponse>> call = RetrofitClientManager.getInstance().getApi().getWordDefinition(word);
        call.enqueue(new Callback<List<DictionaryApiResponse>>() {
            @Override
            public void onResponse(Call<List<DictionaryApiResponse>> call, Response<List<DictionaryApiResponse>> response) {

                if (response.body() != null && response.isSuccessful()){
                    List<DictionaryApiResponse> dictionaryApiResponseList = response.body();
                    onFetchDataListener.onFetchData(dictionaryApiResponseList.get(0), word);
                }else {
//                    meaning.setText(R.string.unableToFetchData);
//                    CREATE ALERT DIALOG HERE TO LINK BACK TO MAIN FRAGMENT ON OK BTN
                    onFetchDataListener.onError("Unable to fetch data from API");
                }
            }
            @Override
            public void onFailure(Call<List<DictionaryApiResponse>> call, Throwable t) {
                onFetchDataListener.onError(t.getMessage());
            }
        });
    }

//   @Override
//   public void onDestroy() {
//        super.onDestroy();
//
//        MainFragment mainFragment = new MainFragment();
//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainFragment).commit();
//   }
}

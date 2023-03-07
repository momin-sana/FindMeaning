package com.student.findmeaning;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.student.findmeaning.Models.DictionaryApiResponse;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientManager {
    private static RetrofitClientManager instance = null;
    Context context;
    DictionaryApi api;
    List<DictionaryApiResponse> dictionaryApiResponseList;

    private RetrofitClientManager(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DictionaryApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(DictionaryApi.class);
    }

    public RetrofitClientManager(Context context) {
        this.context = context;
    }

    public static synchronized RetrofitClientManager getInstance(){
        if (instance == null){
            instance = new RetrofitClientManager();
        }
        return instance;
    }

    public DictionaryApi getApi(){return api;}


//    method to handle calling. here we will pass listener that will capture the response and "word" that we're searching for.
//    for these we create custom interface listener

//    14feb commented
//    public void getWordMeaning(OnFetchDataListener listener, String word) {
////        create obj of our interface
////       api = retrofit.create(DictionaryApi.class);
//        Call<List<DictionaryApiResponse>> call = RetrofitClientManager.getInstance().getApi().getWordDefinition(word);
//
////       getting response, if user couldn't get any response for any word we could show response to that
//        call.enqueue(new Callback<List<DictionaryApiResponse>>() {
//            @Override
//            public void onResponse(@NonNull Call<List<DictionaryApiResponse>> call, @NonNull Response<List<DictionaryApiResponse>> response) {
//                if (response.isSuccessful()) {
//                    dictionaryApiResponseList = response.body();
//
////                    response.body is the <list<DictionaryApiResponse> so for that we have to pass the nth object of api object. get(0) gets the 1st obj
//                    if (dictionaryApiResponseList != null) {
//                        assert response.body() != null;
//                        listener.onFetchData(dictionaryApiResponseList.get(0), response.message());
//                    }
//                } else {
//                    Toast.makeText(context, R.string.retrofit_error, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<List<DictionaryApiResponse>> call, @NonNull Throwable t) {
//                listener.onError("Request failure");
//            }
//        });
//    }

}

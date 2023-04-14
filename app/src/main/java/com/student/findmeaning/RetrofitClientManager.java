package com.student.findmeaning;

import android.annotation.SuppressLint;
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
    @SuppressLint("StaticFieldLeak")
    private static RetrofitClientManager instance = null;
    Context context;
    DictionaryApi api;

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
}

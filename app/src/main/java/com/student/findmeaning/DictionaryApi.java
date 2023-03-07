package com.student.findmeaning;

import com.student.findmeaning.Models.DictionaryApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DictionaryApi {
    //   1. call base url and converter factory
    static final String BASE_URL = "https://api.dictionaryapi.dev/";

    //   2. call end part of url here
//    you would put the word that you want to look up in the dictionary, replacing <word> in the URL. For example, if you wanted to look up the word "cat," you would use the @GET annotation "@GET("cat")"

    @GET("api/v2/entries/en/{word}")
    Call<List<DictionaryApiResponse>> getWordDefinition(@Path("word") String word);

}

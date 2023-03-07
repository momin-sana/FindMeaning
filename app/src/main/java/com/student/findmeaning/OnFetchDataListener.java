package com.student.findmeaning;

import com.student.findmeaning.Models.DictionaryApiResponse;

public interface OnFetchDataListener {
    void onFetchData(DictionaryApiResponse apiResponse, String message);
    void onError(String message);
}

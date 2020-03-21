package com.ozymobilesolutions.crypto.service;

import com.ozymobilesolutions.crypto.model.CryptoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {

    //Retrofit Operations: GET, POST, UPDATE, DELETE

    //GET operation
    //URL base will be write in the retrofit object, in the GET we will add "price?key=xxx"
    //https://api.nomics.com/v1/prices?key=22bad689c2d803b6089ed73a55247f83

    @GET("prices?key=22bad689c2d803b6089ed73a55247f83")
    Call<List<CryptoModel>> getData(); //we set the new method for the get whole JSON , super easy with retrofit



}


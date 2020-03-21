package com.ozymobilesolutions.crypto.model;

import com.google.gson.annotations.SerializedName;

public class CryptoModel {

    // Data modelling is handled here.

    //Model

    @SerializedName("currency")  //without parsing we can read the JSON data
    public String currency;

    @SerializedName("price")
    public String price;


}

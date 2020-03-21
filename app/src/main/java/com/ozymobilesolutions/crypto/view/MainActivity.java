package com.ozymobilesolutions.crypto.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ozymobilesolutions.crypto.R;
import com.ozymobilesolutions.crypto.adapter.RecyclerViewAdapter;
import com.ozymobilesolutions.crypto.model.CryptoModel;
import com.ozymobilesolutions.crypto.service.CryptoAPI;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /*
    In This Project I use nomics.com as API site for whole crypto currencies
    This Application minSdkVersion 23 and targetSdkVersion 28.

    Retrofit and RxJava Libraries used.

    Nomics API Informations
    - API server URI : https://api.nomics.com/v1
    - API key: 22bad689c2d803b6089ed73a55247f83
    - API docs can be found here: https://api.nomics.com/
    - https://api.nomics.com/v1/prices?key=22bad689c2d803b6089ed73a55247f83
        * with this link we can reach all information as JSON format

    Project Main Language is JAVA, the same project will be executed with Kotlin.

    Retrofit: https://square.github.io/retrofit/
              https://github.com/square/retrofit

    RxJava: https://github.com/ReactiveX/RxJava

    In build.gradle(app)

    Up side to the dependencies I set retrofit and rxjava versions

        def retrofitVersion = '2.3.0'
        def rxJavaVersion = '2.1.1'

    In dependencies I make implementations
        implementation "com.squareup.retrofit2:retrofit:$retrofitVersion"
        implementation "com.squareup.retrofit2:converter-gson:$retrofitVersion"
        implementation "com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion"

        implementation "io.reactivex.rxjava2:rxjava:$rxJavaVersion"
        implementation "io.reactivex.rxjava2:rxandroid:$rxJavaVersion"
        implementation 'androidx.recyclerview:recyclerview:1.1.0'

    I create CryptoModel class and in, I define attributes currency, price
    with SerializedName we could get JSON data without parsing, Its Super Easy


    I created new Packages, It makes project more professional


    -adapter
    -model
        -CryptoModel
    -service
    -view
        -MainActivity

    In service we create new Interface and set get operator via Retrofit


       In manifest
       <uses-permission android:name="android.permission.INTERNET"></uses-permission>



     */

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    //Downloading data process variables
    ArrayList<CryptoModel> cryptoModels;
    private String BASE_URL = "https://api.nomics.com/v1/";
    Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //recyclerView
        recyclerView = findViewById(R.id.recyclerView);

        //CryptoModel cryptoModel = new CryptoModel(); //new class declared and initialized // for check , does'nt matter

        //Data Downloading -- Retrofit and JSON

        Gson gson = new GsonBuilder().setLenient().create(); //define gson

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        loadData();



    }

    // Loading Data from API
    private void loadData(){

        CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);

        Call<List<CryptoModel>> call = cryptoAPI.getData();

        //asny. operation
        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if (response.isSuccessful()){
                    List<CryptoModel> responseList  = response.body();
                    cryptoModels = new ArrayList<>(responseList);

                    //Recyclerview operations
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels);
                    recyclerView.setAdapter(recyclerViewAdapter);


                    for (CryptoModel cryptoModel: cryptoModels) {
                        System.out.println(cryptoModel.currency +" - " + cryptoModel.price  );
                    }





                }

            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}

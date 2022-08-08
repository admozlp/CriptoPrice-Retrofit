package com.ademozalp.retrofitjava.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ademozalp.retrofitjava.databinding.ActivityMainBinding;
import com.ademozalp.retrofitjava.model.CryptoModel;
import com.ademozalp.retrofitjava.service.CryptoAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import com.ademozalp.retrofitjava.adapter.Adapter;


public class MainActivity extends AppCompatActivity {

    ArrayList<CryptoModel> cryptoModels;
    String BASE_URL = "https://api.nomics.com/v1/";
    Retrofit retrofit;
    private ActivityMainBinding binding;
    Adapter adapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        // https://api.nomics.com/v1/currencies/ticker?key=key

        Gson gson = new GsonBuilder().setLenient().create();// json dosyası
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))// gelen json dosyasını gson a at
                .build();

        loadData();
    }

    private void loadData(){
        CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);// base url ve request yapılacak kısım birleştrildi
        compositeDisposable.add(cryptoAPI.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse));

        /*
       Call<List<CryptoModel>> call = cryptoAPI.getData();// request işlemi
       call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {//if call is successful
                if(response.isSuccessful()){

                    List<CryptoModel> responseCryptoList = response.body();
                    cryptoModels = new ArrayList<>(responseCryptoList);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    adapter = new Adapter(cryptoModels);
                    binding.recyclerView.setAdapter(adapter);

                    for (CryptoModel cryptoModel : cryptoModels){
                        System.out.println(cryptoModel.currency);
                        System.out.println(cryptoModel.price);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {// if call is failure
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });  */



    }

    private void handleResponse(List<CryptoModel> cryptoModel) {
        cryptoModels  = new ArrayList<>(cryptoModel);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter = new Adapter(cryptoModels);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
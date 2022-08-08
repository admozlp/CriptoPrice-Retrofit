package com.ademozalp.retrofitjava.service;

import com.ademozalp.retrofitjava.model.CryptoModel;
import java.util.List;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {
    // https://api.nomics.com/v1/  // base url
    //currencies/ticker?key=key

    @GET("currencies/ticker?key=key");
    Observable<List<CryptoModel>> getData();
    //Call<List<CryptoModel>> getData();
}

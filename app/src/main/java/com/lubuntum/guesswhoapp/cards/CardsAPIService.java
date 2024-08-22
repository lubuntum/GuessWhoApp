package com.lubuntum.guesswhoapp.cards;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CardsAPIService {
    @GET("uc?export=download")
    Call<List<Card>> getCards(@Query("id") String fileId);
}

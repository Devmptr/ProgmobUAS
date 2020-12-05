package com.devmptr.uasprogmob.network.service;

import com.devmptr.uasprogmob.network.response.LapakResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LapakService {
    @GET("lapak/search/{search}")
    Call<LapakResponse> getLapak(@Path("search") String search);
}

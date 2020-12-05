package com.devmptr.uasprogmob.network.service;

import com.devmptr.uasprogmob.network.response.KategoriIuranResponse;
import com.devmptr.uasprogmob.network.response.LapakResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface KategoriIuranService {
    @GET("getKategoriIuran")
    Call<KategoriIuranResponse> getKategoriIuran();
}

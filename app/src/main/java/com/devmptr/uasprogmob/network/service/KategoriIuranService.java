package com.devmptr.uasprogmob.network.service;

import com.devmptr.uasprogmob.network.response.KategoriIuranResponse;
import com.devmptr.uasprogmob.network.response.LapakResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface KategoriIuranService {
    @GET("getKategoriIuran")
    Call<KategoriIuranResponse> getKategoriIuran();

    @FormUrlEncoded
    @POST("getDataKategori")
    Call<ResponseBody> getDataKategori(@Field("nama_kategori_iuran") String nama_kategori_iuran);
}

package com.devmptr.uasprogmob.network.service;

import com.devmptr.uasprogmob.network.response.KategoriIuranResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BayarIuran {
    @FormUrlEncoded
    @POST("bayarIuran")
    Call<ResponseBody> bayarIuran(@Field("id_lapak") int id_lapak,
                                  @Field("tanggal_bayar") String tanggal_bayar,
                                  @Field("tanggal_iuran") String tanggal_iuran,
                                  @Field("periode_iuran") int periode_iuran,
                                  @Field("id_kategori_iuran") int id_kategori_iuran,
                                  @Field("id_pegawai") int id_pegawai);
}

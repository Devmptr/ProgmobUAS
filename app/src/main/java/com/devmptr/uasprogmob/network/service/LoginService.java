package com.devmptr.uasprogmob.network.service;

import com.devmptr.uasprogmob.network.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginService {
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("loginAdmin")
    Call<LoginResponse> loginAdmin(@Field("username") String username, @Field("password") String password);
}

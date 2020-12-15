package com.devmptr.uasprogmob.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("success")
    @Expose
    private String success;
    private int id_pegawai;
    public  String getSuccess(){
        return success;
    }
    public int getId_pegawai() {
        return id_pegawai;
    }
}

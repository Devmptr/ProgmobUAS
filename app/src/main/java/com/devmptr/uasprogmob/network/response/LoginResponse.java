package com.devmptr.uasprogmob.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("success")
    @Expose
    private String success;
    public  String getSuccess(){
        return success;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    public Integer getId() {
        return id;
    }
}

package com.devmptr.uasprogmob.network.response;

import com.devmptr.uasprogmob.model.LapakModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LapakResponse {
    @SerializedName("lapak")
    @Expose
    private ArrayList<LapakModel> listLapak;
    public ArrayList<LapakModel> getListLapak() {
        return listLapak;
    }
}

package com.devmptr.uasprogmob.network.response;

import com.devmptr.uasprogmob.model.KategoriIuranModel;
import com.devmptr.uasprogmob.model.LapakModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class KategoriIuranResponse {
    @SerializedName("kategoriIuran")
    @Expose
    private ArrayList<KategoriIuranModel> listKategoriIuran;
    public ArrayList<KategoriIuranModel> getListKategoriIuran() {
        return listKategoriIuran;
    }
}

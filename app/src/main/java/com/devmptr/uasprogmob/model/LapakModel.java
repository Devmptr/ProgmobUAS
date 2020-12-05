package com.devmptr.uasprogmob.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LapakModel {
    @SerializedName("id_lapak")
    @Expose
    private int id;

    @SerializedName("nama_lapak")
    @Expose
    private String namaLapak;

    @SerializedName("nama_pemilik")
    @Expose
    private String namaPemilik;

    public String getNamaLapak() {
        return namaLapak;
    }

    public String getNamaPemilik() {
        return namaPemilik;
    }

    public int getId() {
        return id;
    }

}

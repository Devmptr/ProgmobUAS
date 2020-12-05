package com.devmptr.uasprogmob.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KategoriIuranModel {
    @SerializedName("id_kategori_iuran")
    @Expose
    private int id;

    @SerializedName("nama_kategori_iuran")
    @Expose
    private String nama;

    @SerializedName("nilai")
    @Expose
    private String nilai;

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getNilai() {
        return nilai;
    }
}

package com.devmptr.uasprogmob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.devmptr.uasprogmob.adapter.LapakAdapter;
import com.devmptr.uasprogmob.model.KategoriIuranModel;
import com.devmptr.uasprogmob.network.Client;
import com.devmptr.uasprogmob.network.response.KategoriIuranResponse;
import com.devmptr.uasprogmob.network.response.LapakResponse;
import com.devmptr.uasprogmob.network.service.KategoriIuranService;
import com.devmptr.uasprogmob.network.service.LapakService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PembayaranActivity extends AppCompatActivity {
    Spinner spinner;
    List<String> listKategoriIuran;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        spinner = (Spinner) findViewById(R.id.listJenisIuran);
        listKategoriIuran = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listKategoriIuran);
        spinner.setAdapter(adapter);

        getKategoriIuran();
    }

    public void getKategoriIuran(){
        KategoriIuranService service = Client.getClient().create(KategoriIuranService.class);
        Call<KategoriIuranResponse> getKategori = service.getKategoriIuran();
        getKategori.enqueue(new Callback<KategoriIuranResponse>() {
            @Override
            public void onResponse(Call<KategoriIuranResponse> call, Response<KategoriIuranResponse> response) {
                if(response.isSuccessful()){
                    Log.d("response body", response.body().getListKategoriIuran().toString());

                    for(int i = 0; i < response.body().getListKategoriIuran().size(); i++){
                        listKategoriIuran.add(response.body().getListKategoriIuran().get(i).getNama());
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    Log.e("Response errorBody", String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<KategoriIuranResponse> call, Throwable t) {
                Log.e("Response onFailure", t.getMessage().toString());
            }
        });
    }
}
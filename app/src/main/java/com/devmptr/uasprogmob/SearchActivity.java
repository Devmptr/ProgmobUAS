package com.devmptr.uasprogmob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.devmptr.uasprogmob.adapter.LapakAdapter;
import com.devmptr.uasprogmob.model.LapakModel;
import com.devmptr.uasprogmob.network.Client;
import com.devmptr.uasprogmob.network.response.LapakResponse;
import com.devmptr.uasprogmob.network.service.LapakService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    RecyclerView rvLapak;
    LapakAdapter adapter;
    EditText search_form;
    String value = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search_form = (EditText) findViewById(R.id.searchForm);
        search_form.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                value = search_form.getText().toString().trim();
                loadLapak(value);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        loadLapak("");
    }

    public void loadLapak(String val) {
        LapakService service = Client.getClient().create(LapakService.class);
        Call<LapakResponse> allLapak = service.getLapak(val);
        allLapak.enqueue(new Callback<LapakResponse>() {
            @Override
            public void onResponse(Call<LapakResponse> call, Response<LapakResponse> response) {
                if(response.isSuccessful()){
                    show(response.body().getListLapak());
                    Log.d("response body", response.body().getListLapak().toString());
                }else{
                    Log.e("Response errorBody", String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<LapakResponse> call, Throwable t) {
                Log.e("Response onFailure", t.getMessage().toString());
            }
        });
    }

    public void show(ArrayList<LapakModel> list){
        adapter = new LapakAdapter(list);
        rvLapak = (RecyclerView) findViewById(R.id.list_lapak);
        rvLapak.setAdapter(adapter);
        rvLapak.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}
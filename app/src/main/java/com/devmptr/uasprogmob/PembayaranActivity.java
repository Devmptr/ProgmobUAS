package com.devmptr.uasprogmob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.devmptr.uasprogmob.adapter.LapakAdapter;
import com.devmptr.uasprogmob.model.KategoriIuranModel;
import com.devmptr.uasprogmob.network.Client;
import com.devmptr.uasprogmob.network.response.KategoriIuranResponse;
import com.devmptr.uasprogmob.network.response.LapakResponse;
import com.devmptr.uasprogmob.network.service.BayarIuran;
import com.devmptr.uasprogmob.network.service.KategoriIuranService;
import com.devmptr.uasprogmob.network.service.LapakService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PembayaranActivity extends AppCompatActivity {
    Spinner spinner, periodeIuranSpinner;
    EditText tanggal_iuran;
    List<String> listKategoriIuran;
    ArrayAdapter<String> adapter;
    Button btn_submit, iuran_date_picker;
    int id_lapak, id_iuran;
    String tanggal_bayar, formatted_tanggal_iuran;
    SharedPreferences auth_sp;
    Intent intent;
    Integer user_id;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        auth_sp = getApplicationContext().getSharedPreferences("authSP",
                getApplicationContext().MODE_PRIVATE);

        spinner = (Spinner) findViewById(R.id.listJenisIuran);
        tanggal_iuran = findViewById(R.id.tanggalIuran);

        btn_submit = findViewById(R.id.btn_submit_pembayaran);
        receiveData();
        listKategoriIuran = new ArrayList<String>();
        getKategoriIuran();
        tanggal_bayar = datenow();
        periodeIuranSpinner = findViewById(R.id.listPeriodePembayaran);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listKategoriIuran);
        Log.d("priod",periodeIuranSpinner.getSelectedItem().toString());
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedName = adapterView.getItemAtPosition(i).toString();
                requestDataKategori(selectedName);
                Toast.makeText(getApplicationContext(), "Kamu memilih " + selectedName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!auth_sp.contains("log_id")){
                    intent = new Intent(PembayaranActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    user_id = auth_sp.getInt("log_id", 0);
                }
                formatted_tanggal_iuran = format_iuran(tanggal_iuran.getText().toString());
                if(tanggal_iuran.getText().toString().matches("") || periodeIuranSpinner.getSelectedItem().equals("Periode Iuran")){
                    Toast.makeText(getApplicationContext(),"Beberapa form tidak terisi!!", Toast.LENGTH_SHORT).show();
                }else{
                    BayarIuran service = Client.getClient().create(BayarIuran.class);
                    service.bayarIuran(id_lapak, tanggal_bayar, tanggal_iuran.getText().toString(), Integer.parseInt(periodeIuranSpinner.getSelectedItem().toString()),id_iuran, user_id)
                            .enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    Log.d("Call request", call.request().toString());
                                    Log.d("Call request header", call.request().headers().toString());
                                    Log.d("Response raw header", response.headers().toString());
                                    Log.d("Response raw", String.valueOf(response.raw().body()));
                                    Log.d("Response code", String.valueOf(response.code()));
                                    if(response.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "sukses", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Log.e("Response errorBody", String.valueOf(response.code()));
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });
                }
            }
        });

        iuran_date_picker = findViewById(R.id.pickDateIuran);
        iuran_date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IuranDatePicker();
            }
        });
    }

    private void IuranDatePicker() {
        Calendar newCalendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tanggal_iuran.setText(df.format(newDate.getTime()));

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private String format_iuran(String tanggal) {
        try {

            SimpleDateFormat curFormater = new SimpleDateFormat("ddMMyyyy");
            Date dateObj = curFormater.parse(tanggal);
            SimpleDateFormat postFormater = new SimpleDateFormat("yyyy-MM-dd");
            String newDateStr = postFormater.format(dateObj);
            return newDateStr;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return tanggal;
    }

    private void receiveData() {
        Intent intent = getIntent();
        if(intent.getExtras() !=null){
            int int_id_lapak = intent.getIntExtra("lapak_id",0);
            this.id_lapak = int_id_lapak;
        }
    }

    private String datenow() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        String formattedDate = df.format(c);
        Log.d("time",formattedDate);
        return formattedDate;
    }

    private void requestDataKategori(String selectedName) {
        KategoriIuranService service = Client.getClient().create(KategoriIuranService.class);
        service.getDataKategori(selectedName).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        id_iuran = jsonRESULTS.getInt("id_kategori_iuran");
                    } catch (JSONException e){
                        e.printStackTrace();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }else{
                    Log.e("Response errorBody", String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
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
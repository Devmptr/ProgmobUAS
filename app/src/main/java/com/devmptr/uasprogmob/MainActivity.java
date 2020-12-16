package com.devmptr.uasprogmob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.devmptr.uasprogmob.network.Client;
import com.devmptr.uasprogmob.network.response.LoginResponse;
import com.devmptr.uasprogmob.network.service.LoginService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button btn_login;
    EditText username, password;
    LoginService service;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login = (Button) findViewById(R.id.btn_login);
        username = (EditText) findViewById(R.id.usernameForm);
        password = (EditText) findViewById(R.id.passwordForm);
        service = Client.getClient().create(LoginService.class);
        intent = new Intent(MainActivity.this, SearchActivity.class);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(username.getText().toString(), password.getText().toString());
            }
        });
    }

    private void login(String in_username, String in_password){
        Call<LoginResponse> login = service.loginAdmin(in_username, in_password);

        login.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Berhasil Login",
                            Toast.LENGTH_SHORT).show();
                    Log.d("Response body", response.body().getSuccess().toString());
                    SharedPreferences auth_sp = getApplicationContext().getSharedPreferences("authSP",
                            getApplicationContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = auth_sp.edit();
                    Integer user_id = response.body().getId();
                    editor.putInt("log_id", user_id);
                    editor.apply();

                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Gagal Login",
                            Toast.LENGTH_SHORT).show();
                    Log.e("Error body", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error Dev "+t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure body", t.getMessage());
            }
        });
    }
}
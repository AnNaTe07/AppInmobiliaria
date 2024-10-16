package com.softannate.appinmobiliaria;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.softannate.appinmobiliaria.databinding.ActivityLoginBinding;
import com.softannate.appinmobiliaria.modelos.Login;
import com.softannate.appinmobiliaria.modelos.Propietario;
import com.softannate.appinmobiliaria.request.ApiClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class LoginActivityViewModel extends AndroidViewModel {

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void llamarLogin(String mail, String password) {
        Login propietario = new Login(mail, password);
        ApiClient.InmobiliariaService endPointsApi = ApiClient.getApi();
        Call<String> llamadaALoguin = endPointsApi.login(propietario);
        llamadaALoguin.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("salida ", "en camino " + response.raw());
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.d("salida ", "\"en camino  " + response.body());
                        SharedPreferences sp = getApplication().getSharedPreferences("token.xml", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("token", "Bearer " + response.body());
                        Log.d("token ", "\"en camino  " + response.body());
                        editor.commit();
                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("login", true);
                        getApplication().startActivity(intent);
                        Log.d("salida ", "\"en camino a main  " + response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("salida ", t.getMessage());
                Toast.makeText(getApplication(), "Error al Iniciar Sesion", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

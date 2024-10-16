package com.softannate.appinmobiliaria;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.softannate.appinmobiliaria.modelos.Propietario;
import com.softannate.appinmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivityViewModel extends AndroidViewModel {



    private MutableLiveData<Propietario> propietario= new MutableLiveData<>();
    private MutableLiveData<String> error; // Para manejar errores
    private Context contexto;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.contexto = application;
    }

    public LiveData<Propietario> getPropietario() {
        if(propietario == null){
            propietario = new MutableLiveData<>();
        }
        return propietario;
    }

    public LiveData<String> getError() {
        if(error==null){
            error=new MutableLiveData<>();
        }
        return error;
    }

    public void leerPropietario() {
        SharedPreferences sp = contexto.getSharedPreferences("token.xml", 0);
        //String token = sp.getString("token", "");
        String token = sp.getString("token", null);
        if (token == null || token.isEmpty()) {
            Log.e("MainActivity", "Token no disponible");
            // Manejar el error adecuadamente, quizás volver a la pantalla de login
            return;
        }
        ApiClient.InmobiliariaService api = ApiClient.getApi();
        Call<Propietario> llamadaAPerfil = api.profile( token);

        llamadaAPerfil.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                Log.d("salida profile", response.raw() + "");
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        propietario.setValue(response.body());
                        Log.d("salida profile", response.body().toString());
                    } else {
                        error.setValue("No existen datos de propietario");
                        Log.d("salida profile", response.message());
                    }
                } else {
                    error.setValue("Error al obtener datos de propietario. Código: " + response.code());
                    Log.d("Error", "Código de respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                error.setValue("Error al mostrar los Datos: " + t.getMessage());
                Log.d("fallo", t.getMessage());
            }
        });
    }
}

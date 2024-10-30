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


    private MutableLiveData<Propietario> propietario;
    private MutableLiveData<String> avatar;
    private Context contexto;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.contexto = application;
        propietario = new MutableLiveData<>();
        avatar = new MutableLiveData<>();
    }

    public LiveData<Propietario> getPropietario() {
        if(propietario == null){
            propietario = new MutableLiveData<>();
        }
        return propietario;
    }

    public LiveData<String> getAvatar() {
        if(avatar==null){
            avatar=new MutableLiveData<>();
        }
        return avatar;
    }

    public void leerPropietario() {
        ApiClient.Endpoints api = ApiClient.getApi();
        String token = ApiClient.getToken(contexto);

        Call<Propietario> llamadaAPerfil = api.profile(token);
        llamadaAPerfil.enqueue(new Callback<Propietario>() {

            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
               // Log.d("salida profile perfil", response.raw() + "");

                if (response.body() != null) {
                    propietario.setValue(response.body());
                    avatar.setValue(response.body().toString());
                    //Log.d("salida profile", response.body().toString());
                } else {
                   // Log.d("salida ", response.message());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Toast.makeText(contexto, "Error al mostrar los Datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

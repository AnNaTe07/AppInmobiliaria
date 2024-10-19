package com.softannate.appinmobiliaria.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.softannate.appinmobiliaria.modelos.InmueblesContratos;
import com.softannate.appinmobiliaria.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmueblesViewModel extends AndroidViewModel {

    private Context contexto;
    private MutableLiveData<ArrayList<InmueblesContratos>> inmuebles;

    public InmueblesViewModel(@NonNull Application application) {
        super(application);
        this.contexto = application.getApplicationContext();
    }

    public  LiveData<ArrayList<InmueblesContratos>> getMInmuebles(){
        if(inmuebles == null){
            inmuebles = new MutableLiveData<>();
        }
        return inmuebles;
    }

    public void mostrarInmuebles() {

        ApiClient.Endpoints api= ApiClient.getApi();
        String token= ApiClient.getToken(contexto);

        Call<List<InmueblesContratos>> llamadaInmuebles= api.inmuebles(token);
        llamadaInmuebles.enqueue(new Callback<List<InmueblesContratos>>() {
            @Override
            public void onResponse(Call<List<InmueblesContratos>> call, Response<List<InmueblesContratos>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.d("InmueblesViewModel", "Inmuebles: " + response.body().toString());
                    inmuebles.postValue((ArrayList<InmueblesContratos>)response.body());
                    Toast.makeText(contexto, "Éstos son sus inmuebles", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(contexto, "Error al cargar los inmuebles", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<InmueblesContratos>> call, Throwable throwable) {
                Toast.makeText(contexto, "Error al cargar los inmuebles", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
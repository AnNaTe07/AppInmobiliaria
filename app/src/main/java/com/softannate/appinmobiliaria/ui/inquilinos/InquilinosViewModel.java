package com.softannate.appinmobiliaria.ui.inquilinos;

import android.app.Application;
import android.content.Context;
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

public class InquilinosViewModel extends AndroidViewModel {

    private Context contexto;
    private MutableLiveData<ArrayList<InmueblesContratos>> inmuebleContrato;

    public InquilinosViewModel(@NonNull Application application) {
        super(application);
        this.contexto = application.getApplicationContext();

    }

    public LiveData<ArrayList<InmueblesContratos>> getMInmueble(){
        if(inmuebleContrato == null){
            inmuebleContrato = new MutableLiveData<>();
        }
        return inmuebleContrato;
    }

    public void mostrarAlquilados(){
        ApiClient.Endpoints api= ApiClient.getApi();
        String token= ApiClient.getToken(contexto);

        Call<List<InmueblesContratos>> llamadaAlquilados= api.alquilados(token);
        llamadaAlquilados.enqueue(new Callback<List<InmueblesContratos>>() {
            @Override
            public void onResponse(Call<List<InmueblesContratos>> call, Response<List<InmueblesContratos>> response) {

                if(response.isSuccessful() && response.body() != null) {
                    inmuebleContrato.postValue((ArrayList<InmueblesContratos>)response.body());
                    Toast.makeText(contexto, "Inmuebles alquilados", Toast.LENGTH_SHORT).show();
                } else {
                    // Log.e("ContratosViewModel", "Error: " + response.code() + " - " + response.message());
                    Toast.makeText(contexto, "Error al cargar los inmuebles", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<InmueblesContratos>> call, Throwable throwable) {
                // Log.e("ContratosViewModel", "Error en la llamada a la API: " + throwable.getMessage());
                Toast.makeText(contexto, "Error al cargar los inmuebles", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


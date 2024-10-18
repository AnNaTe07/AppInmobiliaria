package com.softannate.appinmobiliaria.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.softannate.appinmobiliaria.modelos.Inmueble;
import com.softannate.appinmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class DetalleInmuebleViewModel extends AndroidViewModel {

    private Context contexto;
    private MutableLiveData<Inmueble> inmueble = new MutableLiveData<>();
    private MutableLiveData<CheckBox> check = new MutableLiveData<>();

    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
        this.contexto = getApplication().getApplicationContext();
        this.inmueble = inmueble;
        this.check = check;
    }

    public LiveData<Inmueble> getInmueble() {
        if (inmueble == null) {
            inmueble = new MutableLiveData<>();
        }
        return inmueble;
    }

    public LiveData<CheckBox> getCheck() {
        if (check == null) {
            check = new MutableLiveData<>();
        }
        return check;
    }

    public void recuperaInmueble(Bundle b){
        Inmueble inmueble = (Inmueble) b.getSerializable("inmueble");
        this.inmueble.postValue(inmueble);
    }

    public void estado(Inmueble i) {

        ApiClient.Endpoints api= ApiClient.getApi();
        String token= ApiClient.getToken(contexto);

        int inmuebleId = i.getId();
        Call<Inmueble> llamadaEstado= api.estado(token,inmuebleId);
        llamadaEstado.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {

                Inmueble inmueble = response.body();
                Toast.makeText(contexto, "Estado de inmueble cambiado", Toast.LENGTH_SHORT).show();

             }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Toast.makeText(contexto, "Estado de inmueble no cambiado", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
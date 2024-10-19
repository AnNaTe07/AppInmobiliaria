package com.softannate.appinmobiliaria.ui.inquilinos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.softannate.appinmobiliaria.modelos.Inquilino;
import com.softannate.appinmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInquilinoViewModel extends AndroidViewModel {

    private Context contexto;
    private MutableLiveData<Inquilino> mInquilino;

    public DetalleInquilinoViewModel(@NonNull Application application) {
        super(application);
        this.contexto = application.getApplicationContext();
    }

    public LiveData<Inquilino> getMInquilino() {
        if (mInquilino == null) {
            mInquilino = new MutableLiveData<>();
        }
        return mInquilino;
    }

    public void leerInquilino(int id) {
        ApiClient.Endpoints api = ApiClient.getApi();
        String token = ApiClient.getToken(contexto);

        Call<Inquilino>llamadaAInquilino = api.inquilino(token, id);
        llamadaAInquilino.enqueue(new Callback<Inquilino>() {

            @Override
            public void onResponse(Call<Inquilino> call, Response<Inquilino> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mInquilino.postValue(response.body());
                } else {
                    Log.e("DetalleInquilinoViewModel", "Error: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Inquilino> call, Throwable t) {
                Log.e("DetalleInquilinoViewModel", "Error en la llamada a la API: " + t.getMessage());
            }
        });
    }
}
package com.softannate.appinmobiliaria.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.softannate.appinmobiliaria.modelos.Contrato;
import com.softannate.appinmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleContratoViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Contrato> contrato;

    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public  LiveData<Contrato> getContrato() {
        if (contrato == null) {
            contrato = new MutableLiveData<>();
        }
        return contrato;
    }

    public void leerContrato(int id){
        ApiClient.Endpoints api= ApiClient.getApi();
        String token=ApiClient.getToken(context);

        Call<Contrato>llamadaAContrato = api.contrato(token, id);
        llamadaAContrato.enqueue(new Callback<Contrato>() {

            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if(response.isSuccessful() && response.body() != null){
                    contrato.postValue(response.body());
                    //Log.d("contratoViewModel", "Contrato: " + response.body().toString());

                }else{
                  //  Log.d("detalleContratoViewModel", "Error: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable throwable) {
               // Log.d("detalleContratoFailure", "Error en la llamada a API: " + throwable.getMessage());
            }
        });
    }
}
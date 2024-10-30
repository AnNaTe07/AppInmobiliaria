package com.softannate.appinmobiliaria.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.softannate.appinmobiliaria.modelos.Pago;
import com.softannate.appinmobiliaria.request.ApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetallePagosViewModel extends AndroidViewModel {

     private Context context;
     private MutableLiveData<ArrayList<Pago>> mPago;

    public DetallePagosViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
        mPago = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Pago>> getMPago() {
        if (mPago == null) {
            mPago = new MutableLiveData<>();
        }
        return mPago;
    }

    public void leerPagos(int id) {
        ApiClient.Endpoints api = ApiClient.getApi();
        String token= ApiClient.getToken(context);

        Call<ArrayList<Pago>>llamadaAPago = api.pagos(token, id);
        llamadaAPago.enqueue(new Callback<ArrayList<Pago>>() {
            @Override
            public void onResponse(Call<ArrayList<Pago>> call, Response<ArrayList<Pago>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    mPago.postValue(response.body());
                    //Log.d("contratoViewModel1", "Contratos: " + response.body().toString());
                }else{
                   // Log.d("contratoViewModel2", "Error: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Pago>>call, Throwable throwable) {
                Log.d("contratoViewModel3", "Error en la llamada a API: " + throwable.getMessage());
            }
        });
    }
}
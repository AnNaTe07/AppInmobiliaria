package com.softannate.appinmobiliaria.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.softannate.appinmobiliaria.R;
import com.softannate.appinmobiliaria.modelos.Propietario;
import com.softannate.appinmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private Context contexto;
    private MutableLiveData<Propietario> mPropietario;
    private MutableLiveData<Boolean> editar = new MutableLiveData<>(false);
    private MutableLiveData<String> button= new MutableLiveData<>("Editar");

    private ApiClient api;

    public LiveData<Propietario> getMPropietario() {
        if (mPropietario == null) {
            mPropietario = new MutableLiveData<>();
        }
        return mPropietario;
    }

    public LiveData<Boolean> getEditar() {
        if (editar == null) {
            editar = new MutableLiveData<>();
        }
        return editar;
    }

    public LiveData<String> getButton() {
        if (button == null) {
            button = new MutableLiveData<>();
        }
        return button;
    }

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        this.contexto = application.getApplicationContext();
    }

    public void leerPropietario() {
        //SharedPreferences sp = contexto.getSharedPreferences("token.xml", 0);
        //String token = sp.getString("token", "");
        ApiClient.Endpoints api = ApiClient.getApi();
        String token = ApiClient.getToken(contexto);

        Call<Propietario> llamadaAPerfil = api.profile(token);
        llamadaAPerfil.enqueue(new Callback<Propietario>() {

            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                Log.d("salida profile perfil", response.raw() + "");

                if (response.body() != null) {
                    mPropietario.setValue(response.body());
                    Log.d("salida profile", response.body().toString());
                } else {
                    Log.d("salida ", response.message());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Toast.makeText(contexto, "Error al mostrar los Datos", Toast.LENGTH_SHORT).show();
                //Log.d("fallo", mPropietario + "");
            }
        });
    }

    public void update(Propietario p){
        ApiClient.Endpoints api = ApiClient.getApi();
        String token = ApiClient.getToken(contexto);

        Call<Propietario> llamadaAActualizar = api.update(token, p);
        Log.d("Datos a actualizar", "Apellido: " + p.getApellido() + ", Nombre: " + p.getNombre() +
            ", Email: " + p.getEmail() + ", Teléfono: " + p.getTelefono());

        llamadaAActualizar.enqueue(new Callback<Propietario>() {

        @Override
        public void onResponse(Call<Propietario> call, Response<Propietario> response) {
           // Log.d("entroOnResponse", response.raw() + "");

            if (response.isSuccessful() && response.body() != null) {
                mPropietario.postValue(response.body());
               // Log.d("respuesta", response.body() + "");
                leerPropietario();
            }
        }
        @Override
        public void onFailure(Call<Propietario> call, Throwable t) {
            Toast.makeText(contexto, "Error al actualizar los Datos", Toast.LENGTH_SHORT).show();
           // Log.d("fallo update profile", t.getMessage());
        }
    });
}

    public void cambioBoton() {
        //Log.d("cambioBoton", "Botón editado clickeado");
        if (editar.getValue() != null && editar.getValue()) {
            editar.setValue(false);
            button.setValue("Editar");
            leerPropietario();
        } else {
            editar.setValue(true); // Activo el modo de edición
            button.setValue("Guardar");
        }
    }

    public void cambioEditText (ViewGroup layout){
            boolean editable = editar.getValue() != null && editar.getValue();
            for (int i = 0; i < layout.getChildCount(); i++) {// n° hijos
                View child = layout.getChildAt(i);
                if (child instanceof EditText) {
                    child.setFocusable(editable);
                    child.setFocusableInTouchMode(editable);
                    child.setEnabled(editable);
                }
            }
        }

    }



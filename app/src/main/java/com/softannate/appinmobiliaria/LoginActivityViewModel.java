package com.softannate.appinmobiliaria;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.softannate.appinmobiliaria.modelos.Login;
import com.softannate.appinmobiliaria.modelos.OlvidaPass;
import com.softannate.appinmobiliaria.request.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {

    private MutableLiveData<String> mensaje;


    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
    }

    //mutable para mensaje
    public LiveData<String> getMensaje() {
        if (mensaje == null) {
            mensaje = new MutableLiveData<>();
        }
        return mensaje;
    }

    public boolean validarEmail(String email) {
        if (email.isEmpty()) {
            mensaje.setValue("Por favor, ingresa tu email.");
            return false; // Email no válido
        }
        return true; // Email válido
    }

    public void validarLogin(String email, String password) {
        if (!validarEmail(email)) {
            return; // Salgo si el email no es válido
        }
        if (password.isEmpty()) {
            mensaje.setValue("Por favor, ingresa tu password.");
            return; // Salgo si el pass está vacío
        }

        // Si ambos campos son válidos, llamo al método para iniciar sesión
        llamarLogin(email, password);
    }



    public void llamarLogin(String email, String password) {
        Login propietario = new Login(email, password);
        ApiClient.Endpoints api = ApiClient.getApi();
        Call<String> llamadaALoguin = api.login(propietario);
        llamadaALoguin.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("salida ", "en camino " + response.raw());
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ApiClient.guardarToken(("Bearer " + response.body()), getApplication() );
                        Log.d("salida ", "\"en camino  " + response.body());

                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("login", true);
                        getApplication().startActivity(intent);
                        Log.d("salida ", "\"en camino a main  " + response.body());
                        Toast.makeText(getApplication(), "Bienvenido a inmobiliaria AnNaTe", Toast.LENGTH_SHORT).show();
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

    public void validarRestablecerPass(String email) {
        if (!validarEmail(email)) {
            return; // Salgo si el email no es válido
        }
        enviarEmailParaRestablecer(email);
    }

    public void enviarEmailParaRestablecer(String email) {

        OlvidaPass olvidaPass = new OlvidaPass(email);

        ApiClient.Endpoints api =   ApiClient.getApi();
        Call<ResponseBody> llamadaAOlvidaPass = api.enviarEmail(olvidaPass);

        llamadaAOlvidaPass.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Email enviado para restablecer la contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("API Error", "Código: " + response.code() + ", Mensaje: " + response.message());
                    Toast.makeText(getApplication(), "Email no encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("API Error", "Error: " + t.getMessage());
                Toast.makeText(getApplication(), "Error al enviar el email", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

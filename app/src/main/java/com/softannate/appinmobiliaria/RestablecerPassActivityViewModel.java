package com.softannate.appinmobiliaria;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.softannate.appinmobiliaria.modelos.RestablecePass;
import com.softannate.appinmobiliaria.request.ApiClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class RestablecerPassActivityViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> passValido; //si el pass es válido
    private MutableLiveData<String> mensaje; // para mensajes
    private String email; // Almaceno el email.
    private String token; // Almaceno el token.
    private MutableLiveData<Boolean> redirigirAlLogin;



    public RestablecerPassActivityViewModel(@NonNull Application application) {
        super(application);
        passValido = new MutableLiveData<>();
        mensaje = new MutableLiveData<>();
        redirigirAlLogin = new MutableLiveData<>();
    }

    //para obtener validez del pass.
    public LiveData<Boolean> getPassValido() {
        if (passValido == null) {
            passValido = new MutableLiveData<>();
        }
        return passValido;
    }

    //para obtener el mensaje.
    public LiveData<String> getMensaje() {
        if (mensaje == null) {
            mensaje = new MutableLiveData<>();
        }
        return mensaje;
    }

    //para cerrar el activity
    public LiveData<Boolean> getRedirigirAlLogin() {
        if (redirigirAlLogin == null) {
            redirigirAlLogin = new MutableLiveData<>();
        }
        return redirigirAlLogin;
    }

    // para establecer el email.
    public void setEmail(String email) {
        this.email = email; // seteo el email recibido.
    }

    // para obtener el email almacenado.
    public String getEmail() {
        return email;
    }

    //para establecer el token.
    public void setToken(String token) {
        this.token = token; // seteo el token recibido.
    }

    //para obtener el token almacenado.
    public String getToken() {
        return token;
    }

    //para validar y restablecer el pass.
    public void validarYRestablecerPassword(String nuevoPass, String confirmacionPass) {
        if (nuevoPass.isEmpty() || confirmacionPass.isEmpty()) {
            mensaje.setValue("Los campos deben ser completados.");
            passValido.setValue(false); // pass no es válido.
            return; // Salgo si están vacíos.
        }
        if (!nuevoPass.equals(confirmacionPass)) {
            mensaje.setValue("Los password no coinciden.");
            passValido.setValue(false); // pass no es válido.
            return; // Salgo si no coinciden.
        }

        // Si los pass son válidos, llamo a restablecerPassword.
        passValido.setValue(true); // pass válido.
        restablecerPassword(email, token, nuevoPass);
    }

    // para restablecer el pass
    public void restablecerPassword(String email, String token, String nuevoPass) {

        String token2 = "Bearer " + token;

        RestablecePass dto = new RestablecePass(token, email, nuevoPass);  // dto para enviar.

        ApiClient.Endpoints api = ApiClient.getApi();
        Call<ResponseBody> llamadaARestablecerPass = api.restablecerPass(dto, token2);

        llamadaARestablecerPass.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Password restablecida correctamente.", Toast.LENGTH_SHORT).show();
                    redirigirAlLogin.setValue(true);
                } else {
                    Toast.makeText(getApplication(), "Error al restablecer el Password", Toast.LENGTH_SHORT).show();
                   // Log.e("RestablecerPass", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Log.e("RestablecerPass", "Fallo en la solicitud: " + t.getMessage());
            }
        });
    }
}

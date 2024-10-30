package com.softannate.appinmobiliaria.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.softannate.appinmobiliaria.modelos.Inmueble;
import com.softannate.appinmobiliaria.modelos.Tipo;
import com.softannate.appinmobiliaria.modelos.UsoInmueble;
import com.softannate.appinmobiliaria.request.ApiClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevoInmuebleViewModel extends AndroidViewModel {

    private MutableLiveData<ResponseBody> mInmueble;
    private Context contexto;
    private MutableLiveData<List<UsoInmueble>> usoInmuebleList;
    private MutableLiveData<Integer> usoInmuebleId;
    private MutableLiveData<List<Tipo>> tipoList;
    private MutableLiveData<Integer> tipoId;
    private MutableLiveData<CloseFEvent> cierroActivity ;


    public class CloseFEvent {
        //señal para notificar que se debe cerrar el fragment
    }

    //para notificar que se debe cerrar el fragment
    public LiveData<CloseFEvent> getCierroActivity() {
        if (cierroActivity == null) {
            cierroActivity = new MutableLiveData<>();
        }
        return cierroActivity;
    }


    //Contructor
    public NuevoInmuebleViewModel(@NonNull Application application) {
        super(application);
        this.contexto = application.getApplicationContext();
        mInmueble = new MutableLiveData<>();
        usoInmuebleList = new MutableLiveData<>();
        usoInmuebleId = new MutableLiveData<>(-1);
        tipoId = new MutableLiveData<>(-1);
        tipoList = new MutableLiveData<>();
        cargarUsos();
        cargarTipos();
    }


    //devuelvo la lista de usos
    public LiveData<List<UsoInmueble>> getUsoInmueble(){
        if(usoInmuebleList == null) {
            usoInmuebleList = new MutableLiveData<List<UsoInmueble>>();
        }
        return usoInmuebleList;
    }

    //devuelvo la lista de tipos
    public LiveData<List<Tipo>> getTipoList(){
        if(tipoList == null) {
            tipoList = new MutableLiveData<List<Tipo>>();
        }
        return tipoList;
    }

    //para enviar el id de tipo
    public LiveData<Integer> getTipoId() {
        if (tipoId == null) {
            tipoId = new MutableLiveData<Integer>(-1);
        }
        return tipoId;
    }

    //par enviar el id del uso seleccionado
    public LiveData<Integer> getUsoInmuebleId() {
        if (usoInmuebleId == null) {
            usoInmuebleId = new MutableLiveData<Integer>();
        }
        return usoInmuebleId;
    }

    //validaciones
    public void validarLatitud(String input) {
        if (input.isEmpty()) {
            return;
        }
        try {
            BigDecimal latitud = new BigDecimal(input);
            if (latitud.compareTo(new BigDecimal(-90)) < 0 || latitud.compareTo(new BigDecimal(90)) > 0) {
                Toast.makeText(contexto, "La latitud debe estar entre -90 y 90.", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(contexto, "Formato de latitud no válido.", Toast.LENGTH_SHORT).show();
        }
    }

    public void validarLongitud(String input) {
        if (input.isEmpty()) {
            return;
        }

        try {
            BigDecimal longitud = new BigDecimal(input);
            if (longitud.compareTo(new BigDecimal(-180)) < 0 || longitud.compareTo(new BigDecimal(180)) > 0) {

                Toast.makeText(contexto, "La longitud debe estar entre -180 y 180.", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(contexto, "Formato de longitud no válido.", Toast.LENGTH_SHORT).show();
        }
    }

    //llamada a la API para cargar los tipos
    private void cargarTipos(){
        ApiClient.Endpoints api= ApiClient.getApi();

        Call<List<Tipo>> llamadaATipo = api.tipos();
        llamadaATipo.enqueue(new Callback<List<Tipo>>() {

            @Override
            public void onResponse(retrofit2.Call<List<Tipo>> call, Response<List<Tipo>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        //Log.d("API Response", "Tipos: " + response.body().size());
                        //Log.d("API Response", "Tipos: " + response.body().toString());
                        tipoList.postValue(response.body());
                    } else {
                        Log.d("API Response", "Respuesta vacía.");
                    }
                } else {
                    Log.d("API Response", "Error en la respuesta: " + response.message());
                }
            }


            @Override
            public void onFailure(retrofit2.Call<List<Tipo>> call, Throwable throwable) {

            }
        });
    }

    //llamada a la API para cargar los usos
    private void cargarUsos(){
        ApiClient.Endpoints api= ApiClient.getApi();

        Call<List<UsoInmueble>> llamadaAUso = api.usos();
        llamadaAUso.enqueue(new Callback<List<UsoInmueble>>() {

            @Override
            public void onResponse(retrofit2.Call<List<UsoInmueble>> call, Response<List<UsoInmueble>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        //Log.d("API Response", "Usos: " + response.body().size());
                        //Log.d("API Response", "Usos: " + response.body().toString());
                        usoInmuebleList.postValue(response.body());
                    } else {
                        Log.d("API Response2", "Respuesta vacía.");
                    }
                } else {
                    Log.d("API Response2", "Error en la respuesta: " + response.message());
                }
            }


            @Override
            public void onFailure(retrofit2.Call<List<UsoInmueble>> call, Throwable throwable) {

            }
        });
    }

    //para seleccionar el tipo y obtener el id
    public void setTipoSeleccionado(int posicion) {
        if (tipoList.getValue() != null && posicion < tipoList.getValue().size()) {
            Tipo tipoSeleccionado = tipoList.getValue().get(posicion);
            tipoId.setValue(tipoSeleccionado.getId());
            Log.d("Tipo seleccionado", "ID: " + tipoSeleccionado.getId());
        } else {
            tipoId.setValue(-1); // Valor por defecto
        }
    }

    // para seleccionar el uso y obtener el ID
    public void setUsoSeleccionado(int posicion) {
        if (usoInmuebleList.getValue() != null && posicion < usoInmuebleList.getValue().size()) {
            UsoInmueble usoSeleccionado = usoInmuebleList.getValue().get(posicion);
            usoInmuebleId.setValue(usoSeleccionado.getId());
            Log.d("uso seleccionado", "ID: " + usoSeleccionado.getId());
        } else {
            usoInmuebleId.setValue(-1); // Valor por defecto
        }
    }

    //devuelvo el inmueble
    public LiveData<ResponseBody> getInmueble() {
        if (mInmueble == null) {
            mInmueble = new MutableLiveData<ResponseBody>();
        }
        return mInmueble;
    }

    //para crear el inmueble
    public void nuevoInmueble(Inmueble inmuebleE, Uri imageUri) {
        Log.d("NuevoInmueble", "Método llamado con URI: " + imageUri);

        //directorio donde se almacenará la imagen
        File directory = new File(contexto.getCacheDir(), "images");
        if (!directory.exists()) {
            directory.mkdirs(); // Crea el directorio si no existe
        }

        // se copia el contenido de la URI a imageFile
        File imageFile = new File(directory, "inmueble_image.jpg");
        try (InputStream inputStream = contexto.getContentResolver().openInputStream(imageUri)) {
            if (inputStream == null) {
                Log.e("Image Error", "InputStream is null. Check the URI.");
                return; // Termina si el InputStream es nulo
            }

            // Inicializa el archivo de salida
            try (FileOutputStream outputStream = new FileOutputStream(imageFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
            }

            // Verifica que el archivo se haya creado
            if (!imageFile.exists() || imageFile.length() == 0) {
                Log.e("Error", "El archivo de imagen no existe o está vacío después de copiar.");
                return;
            }

        } catch (FileNotFoundException e) {
            Log.e("Image Error", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.e("Image Error", "IO exception: " + e.getMessage());
        }

        // Resto del código para enviar la solicitud a la API...
        ApiClient.Endpoints api = ApiClient.getApi();
        String token = ApiClient.getToken(contexto);

        //RequestBody para cada campo
        RequestBody usoInmuebleId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(inmuebleE.getUsoId()));
        RequestBody tipoId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(inmuebleE.getTipoId()));
        RequestBody ambientes = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(inmuebleE.getAmbientes()));
        RequestBody direccion = RequestBody.create(MediaType.parse("text/plain"), inmuebleE.getDireccion());
        RequestBody latitud = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(inmuebleE.getLatitud()));
        RequestBody longitud = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(inmuebleE.getLongitud()));
        RequestBody precio = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(inmuebleE.getPrecio()));
        RequestBody superficie = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(inmuebleE.getSuperficie()));

        // RequestBody para foto
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
        MultipartBody.Part foto = MultipartBody.Part.createFormData("foto", imageFile.getName(), requestFile);


        Call<ResponseBody> llamadaANuevo = api.create(token, usoInmuebleId, tipoId, ambientes, direccion, latitud, longitud, precio, superficie, foto);
        llamadaANuevo.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    mInmueble.setValue(response.body());
                    //Log.d("API Response280", "Inmueble: " + response.body().toString());
                    Toast.makeText(contexto, "Inmueble registrado con éxito.", Toast.LENGTH_SHORT).show();
                    cierroActivity.postValue(new CloseFEvent());//emito el evento
                } else {
                    String errorBody = null;
                    try {
                        errorBody = response.errorBody().string();// .string() para obtener el contenido

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Log.d("API Response", "Error en la respuesta: " + response.message() + " Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.d("API Response", "Error en la llamada a API: " + throwable.getMessage());
                throwable.printStackTrace();

            }
        });
    }
}
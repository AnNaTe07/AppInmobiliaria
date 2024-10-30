package com.softannate.appinmobiliaria.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class DetalleInmuebleViewModel extends AndroidViewModel {

    private Context contexto;
    private MutableLiveData<Inmueble> inmueble = new MutableLiveData<>();
    private MutableLiveData<CheckBox> check = new MutableLiveData<>();
    private MutableLiveData<String> foto;
    private int inmuebleId;

    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
        foto=new MutableLiveData<>();
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

    public LiveData<String> getFoto() {
        if (foto == null) {
            foto = new MutableLiveData<>();
        }
        return foto;
    }

    public void recuperaInmueble(Bundle b){
        Inmueble inmueble = (Inmueble) b.getSerializable("inmueble");
        this.inmueble.postValue(inmueble);
        if(inmueble != null)
        {
        this.inmuebleId = inmueble.getId();}
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

    public void subirFoto(Uri uri, Context context) {

        Log.d("subirFoto", uri.toString());
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);

            Log.d("subirFoto", "InputStream obtenido exitosamente");

            File file = new File(context.getCacheDir(), "inmueble.jpg");
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;

            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.close();
            inputStream.close();
            Log.d("subirFoto", "Archivo de imagen guardado en: " + file.getAbsolutePath());


            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part fotoInmueble = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

            ApiClient.Endpoints api = ApiClient.getApi();
            String token = ApiClient.getToken(context);


            Log.d("subirFoto", "Llamando a la API para subir la foto con ID: " + inmuebleId);
            Call<ResponseBody> llamadaAFoto = api.updateFoto(token, fotoInmueble, inmuebleId);
            llamadaAFoto.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        //Log.d("subirFoto", "Foto subida exitosamente");
                        try {
                            // Asigno la URL
                            foto.setValue(response.body().string());
                            Toast.makeText(context, "Foto de inmueble actualizada correctamente", Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                           // Log.e("subirFoto", "Error al obtener la URL de la respuesta: " + e.getMessage());
                            throw new RuntimeException(e);
                        }
                    } else {
                      //  Log.d("subirFoto", "Error al subir la foto: " + response.code() + " - " + response.message());
                        //Log.d("error", response.code() + " - " + response.message());
                        Toast.makeText(context, "Error al subir la foto del inmueble: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                    //Log.d("errorFoto", throwable.getMessage());
                    Toast.makeText(context, "Fallo al subir la foto del inmueble", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
           // Log.e("subirFoto", "Error al obtener el archivo de la imagen: " + e.getMessage());
            //Log.d("errorFoto", e.getMessage());
            Toast.makeText(context, "Error al obtener el archivo de la imagen: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
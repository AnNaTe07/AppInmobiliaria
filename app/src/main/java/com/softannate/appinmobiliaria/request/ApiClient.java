package com.softannate.appinmobiliaria.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.session.MediaSession;
import android.support.v4.media.session.MediaSessionCompat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softannate.appinmobiliaria.modelos.Login;
import com.softannate.appinmobiliaria.modelos.Propietario;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public class ApiClient {

    public static final String URL_BASE= "http://192.168.1.2:5000/api/";
    //private static SharedPreferences sp;

    public static InmobiliariaService getApi(){

        Gson gson = new GsonBuilder().setLenient().create();//parseo de elemento json a objeto java

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))//para objetos complejos como fechas
                .build();

        return  retrofit.create(InmobiliariaService.class);
    }


    public interface InmobiliariaService {
        @POST("propietario/login")
        Call<String> login(@Body Login login);

        @GET("propietario/profile")
        Call<Propietario> profile(@Header("Authorization") String token);

        @PUT("propietario/update")
        Call<Propietario> update(@Header("Authorization") String token,@Body Propietario propietario);


    }


    //@FormUrlEncoded
    //@POST("propietario/login")
    //Call<String> login(@Field("Email") String mail, @Field("Clave") String clave);
}

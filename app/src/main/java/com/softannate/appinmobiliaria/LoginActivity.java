package com.softannate.appinmobiliaria;

import static android.Manifest.permission.*;
import android.content.pm.PackageManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.softannate.appinmobiliaria.databinding.ActivityLoginBinding;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private LoginActivityViewModel vm;
    private ActivityLoginBinding binding;
    private SensorManager sm;
    private Sensor acelerometro;
    private boolean movimiento = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginActivityViewModel.class);
        setContentView(binding.getRoot());

        ImageView logo = findViewById(R.id.logo);
        logo.setImageResource(R.drawable.logo_inmo);


        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensores = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensores.size() > 0) {
            acelerometro = sensores.get(0);
            sm.registerListener(new LeeSensor(), acelerometro, SensorManager.SENSOR_DELAY_GAME);
        }

        binding.tvOlvidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RestablecerPassActivity.class);
                startActivity(intent);
            }
        });

        binding.btIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.llamarLogin(binding.etEmail.getText().toString(), binding.etPass.getText().toString());
            }
        });

        //para agregar a el texto el subrayado
        binding.tvOlvidePass.setPaintFlags(binding.tvOlvidePass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void solicitarPermiso(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M
                && checkSelfPermission(CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{CALL_PHONE},1000);
        }
    }

    private void llamar() {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:2664"));
            startActivity(intent);
            solicitarPermiso();
    }


    private class LeeSensor implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            double aceleracion = Math.sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH;
            if (aceleracion > 2.0 && !movimiento) {
                movimiento = true;
                //llamar();
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        // Registrar el listener del sensor directamente
        sm.registerListener(new LeeSensor(), acelerometro, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Desregistrar el listener del sensor
        sm.unregisterListener(new LeeSensor());
    }

}
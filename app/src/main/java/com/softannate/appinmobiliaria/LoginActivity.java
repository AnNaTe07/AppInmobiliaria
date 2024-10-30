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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.softannate.appinmobiliaria.databinding.ActivityLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private LoginActivityViewModel vm; // Declaro el ViewModel para el activity del login
    private ActivityLoginBinding binding; // Binding para acceder a las vistas del activity
    private SensorManager sm; // Manager para manejar sensores
    private Sensor acelerometro; // Sensor acelerómetro
    private boolean movimiento = false; // Variable para controlar el estado del movimiento

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityLoginBinding.inflate(getLayoutInflater()); // Inflo el layout de la actividad
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginActivityViewModel.class); // Inicializo el ViewModel
        setContentView(binding.getRoot());

        //logo de la app
        ImageView logo = findViewById(R.id.logo);
        logo.setImageResource(R.drawable.logo_inmo);

        // Inicializo el SensorManager y registro el acelerómetro
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensores = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensores.size() > 0) {
            acelerometro = sensores.get(0); //primer sensor acelerómetro disponible
            sm.registerListener(new LeeSensor(), acelerometro, SensorManager.SENSOR_DELAY_GAME); // Registro el listener para el sensor
        }

        //evento  "Olvidé pass"
        binding.tvOlvidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.etEmail.getText().toString().trim(); // email del campo de texto
                vm.validarRestablecerPass(email); // Llamo al método de validación en el ViewModel
            }
        });

        //observer para el mensaje
        vm.getMensaje().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String mensaje) {
                Toast.makeText(LoginActivity.this, mensaje, Toast.LENGTH_SHORT).show();
            }
        });

        // evento para el botón de ingreso
        binding.btIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.etEmail.getText().toString().trim();
                String password = binding.etPass.getText().toString().trim();

                // Llamo al método del ViewModel para validar el login
                vm.validarLogin(email, password);
            }
        });

        // Agrego subrayado al texto de "Olvidé pass"
        binding.tvOlvidePass.setPaintFlags(binding.tvOlvidePass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // permiso para realizar llamadas telefónicas
    private void solicitarPermiso() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{CALL_PHONE}, 1000); //permiso de llamada
        }else {
            llamar();
        }
    }

    // Método para realizar la llamada telefónica
    private void llamar() {
        Intent intent = new Intent(Intent.ACTION_CALL); // Creo un intent para llamar
        intent.setData(Uri.parse("tel:2664334839")); // Establezco el número de teléfono
        startActivity(intent); // Inicio la actividad para realizar la llamada
        //solicitarPermiso(); // Solicito el permiso para llamar
    }


    // Clase interna para manejar eventos del sensor
    private class LeeSensor implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0]; // Obtengo el valor en el eje X
            float y = event.values[1]; // Obtengo el valor en el eje Y
            float z = event.values[2]; // Obtengo el valor en el eje Z

            // Calculo la aceleración total
            double aceleracion = Math.sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH;
            if (aceleracion > 2.0 && !movimiento) {
                movimiento = true; // Cambio el estado del movimiento
                solicitarPermiso();

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Registro el listener del sensor al reanudar la actividad
        sm.registerListener(new LeeSensor(), acelerometro, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Desregistro el listener del sensor al pausar la actividad
        sm.unregisterListener(new LeeSensor());
    }
}

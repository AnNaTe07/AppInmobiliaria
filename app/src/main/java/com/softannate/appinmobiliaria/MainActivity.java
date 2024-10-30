package com.softannate.appinmobiliaria;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.softannate.appinmobiliaria.databinding.ActivityMainBinding;
import com.softannate.appinmobiliaria.modelos.Propietario;
import com.softannate.appinmobiliaria.request.ApiClient;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private MainActivityViewModel vm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        vm = new ViewModelProvider(this).get(MainActivityViewModel.class);


        // Obtengo el token
        SharedPreferences sp = getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        String token = sp.getString("token", null);
        Log.d("token profile", token);

        setSupportActionBar(binding.appBarMain.toolbar);

        View headerView = binding.navView.getHeaderView(0);
        TextView hNombre = headerView.findViewById(R.id.headerNombre);
        TextView hEmail = headerView.findViewById(R.id.headerEmail);
        ImageView hAvatar = headerView.findViewById(R.id.headerAvatar);

        //seteo datos de propietario en header
        vm.getPropietario().observe(this, new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                hNombre.setText(propietario.getNombre()+" "+ propietario.getApellido());
                hEmail.setText(propietario.getEmail());
                Log.d("Avatar URL", "URL: " + propietario.getAvatar());
                vm.getAvatar().observe(MainActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        Glide.with(getApplication().getApplicationContext()).
                                load(propietario.getAvatar())
                                .placeholder(R.drawable.cargando) // Imagen temporal mientras se carga
                                .diskCacheStrategy(DiskCacheStrategy.NONE) // Desactivo la caché
                                .skipMemoryCache(true)
                                .signature(new ObjectKey(System.currentTimeMillis())) // Forzar actualización
                                .error(R.drawable.perfil_user)
                                .into(hAvatar);
                    }
                });
            }
        });


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_ubicacion, R.id.nav_perfil, R.id.nav_inmuebles,
                R.id.nav_inquilinos, R.id.nav_contratos, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Para sacar el color que trae por defecto cada item del menú
        navigationView.setItemIconTintList(null);

        // Listener para cuando se abre el menú
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                vm.leerPropietario(); // Llama a leerPropietario cuando el menú se abre
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {}

            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {}

            @Override
            public void onDrawerStateChanged(int newState) {}
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        vm.leerPropietario();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

}

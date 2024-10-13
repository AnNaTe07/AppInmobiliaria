package com.softannate.appinmobiliaria;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.softannate.appinmobiliaria.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginActivityViewModel vm;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        vm= ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginActivityViewModel.class);
        setContentView(binding.getRoot());

        ImageView logo = findViewById(R.id.logo);
        logo.setImageResource(R.drawable.logo_inmo);

        //TextView tvOlvidePass = binding.tvOlvidePass;
        binding.tvOlvidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, RestablecerPassActivity.class);
                startActivity(intent);
            }
        });

        //para agregar a el texto el subrayado
        binding.tvOlvidePass.setPaintFlags(binding.tvOlvidePass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        //binding.etPass.setPaintFlags(binding.etPass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
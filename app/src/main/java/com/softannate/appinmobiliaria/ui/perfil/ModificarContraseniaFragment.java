package com.softannate.appinmobiliaria.ui.perfil;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.softannate.appinmobiliaria.LoginActivity;
import com.softannate.appinmobiliaria.R;
import com.softannate.appinmobiliaria.databinding.FragmentModificarContraseniaBinding;
import com.softannate.appinmobiliaria.modelos.Pass;

public class ModificarContraseniaFragment extends Fragment {

    private ModificarContraseniaViewModel vmPass;
    private FragmentModificarContraseniaBinding binding;

    private EditText etPassActual, etNuevaPass, etConfirmaPass;

    public static ModificarContraseniaFragment newInstance() {
        return new ModificarContraseniaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vmPass = new ViewModelProvider(this).get(ModificarContraseniaViewModel.class);
        binding = FragmentModificarContraseniaBinding.inflate(inflater, container, false);

        binding.btGuardarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPassActual = binding.etPassR1;
                etNuevaPass = binding.etPassR2;
                etConfirmaPass = binding.etPassR3;
                String passActual = etPassActual.getText().toString();
                String nuevaPass = etNuevaPass.getText().toString();
                String confirmaPass = etConfirmaPass.getText().toString();
                vmPass.cambiarPass(passActual, nuevaPass, confirmaPass);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        vmPass.getRedirigirAlLogin().observe(getViewLifecycleOwner(), redirigir -> {
            // Si es true, redirige al login
            redirigirAlLogin();
        });
    }
    private void redirigirAlLogin() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//se eliminan las activity, sólo la nueva permanece, asi el usuario no puede volver atrás
        startActivity(intent);
        getActivity().finish();
    }
    }


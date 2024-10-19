package com.softannate.appinmobiliaria.ui.inquilinos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softannate.appinmobiliaria.R;
import com.softannate.appinmobiliaria.databinding.FragmentDetalleInquilinoBinding;
import com.softannate.appinmobiliaria.modelos.Inquilino;

public class DetalleInquilinoFragment extends Fragment {

    private TextView tvDni, tvNombre, tvApellido, tvEmail, tvTelefono;
    private DetalleInquilinoViewModel vmInquilino;
    private FragmentDetalleInquilinoBinding binding;

    public static DetalleInquilinoFragment newInstance() {
        return new DetalleInquilinoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding=FragmentDetalleInquilinoBinding.inflate(inflater, container, false);
        vmInquilino=new ViewModelProvider(this).get(DetalleInquilinoViewModel.class);

        //inicializo campos
        tvDni = binding.tvDni;
        tvEmail = binding.tvEmail;
        tvNombre = binding.tvNombre;
        tvTelefono = binding.tvTel;

        // Recupero el ID del inquilino desde  getArguments
        if (getArguments() != null) {
            int inquilinoId = getArguments().getInt("inquilinoId", -1);
            if (inquilinoId != -1) {
                vmInquilino.leerInquilino(inquilinoId);
            }
        }

        vmInquilino.getMInquilino().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
            @Override
            public void onChanged(Inquilino inquilino) {
                tvDni.setText(inquilino.getDni()+"");
                tvEmail.setText(String.valueOf(inquilino.getEmail()));
                tvNombre.setText(String.valueOf(inquilino.getNombreCompleto()));
                tvTelefono.setText(String.valueOf(inquilino.getTelefono()));
            }
        });

        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vmInquilino = new ViewModelProvider(this).get(DetalleInquilinoViewModel.class);
        // TODO: Use the ViewModel
    }

}
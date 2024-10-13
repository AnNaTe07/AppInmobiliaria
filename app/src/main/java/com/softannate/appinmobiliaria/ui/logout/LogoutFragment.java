package com.softannate.appinmobiliaria.ui.logout;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softannate.appinmobiliaria.R;
import com.softannate.appinmobiliaria.databinding.FragmentLogoutBinding;

public class LogoutFragment extends Fragment {

    private FragmentLogoutBinding bindingL;

    public static LogoutFragment newInstance() {
        return new LogoutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        bindingL = FragmentLogoutBinding.inflate(inflater, container, false);
        return bindingL.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mostrarDialogo();
    }
    private void mostrarDialogo(){
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirmar salida")
                .setMessage("¿Está seguro que desea salir?")
                .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requireActivity().finishAffinity();
            }
        }).setNegativeButton("Cancelar", null).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bindingL = null;
    }

}
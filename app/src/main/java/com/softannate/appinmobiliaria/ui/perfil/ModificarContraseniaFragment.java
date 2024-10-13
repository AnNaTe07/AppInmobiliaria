package com.softannate.appinmobiliaria.ui.perfil;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softannate.appinmobiliaria.R;

public class ModificarContraseniaFragment extends Fragment {

    private ModificarContraseniaViewModel mViewModel;

    public static ModificarContraseniaFragment newInstance() {
        return new ModificarContraseniaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_modificar_contrasenia, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ModificarContraseniaViewModel.class);
        // TODO: Use the ViewModel
    }

}
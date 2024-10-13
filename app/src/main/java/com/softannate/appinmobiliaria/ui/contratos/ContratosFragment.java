package com.softannate.appinmobiliaria.ui.contratos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softannate.appinmobiliaria.R;
import com.softannate.appinmobiliaria.databinding.FragmentContratosBinding;

public class ContratosFragment extends Fragment {

    private ContratosViewModel vmContratos;
    private FragmentContratosBinding bindingCo;

    public static ContratosFragment newInstance() {
        return new ContratosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        bindingCo = FragmentContratosBinding.inflate(inflater, container, false);

        return bindingCo.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vmContratos = new ViewModelProvider(this).get(ContratosViewModel.class);
        // TODO: Use the ViewModel
    }

}
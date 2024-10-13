package com.softannate.appinmobiliaria.ui.inmuebles;

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
import com.softannate.appinmobiliaria.databinding.FragmentNuevoInmuebleBinding;

public class NuevoInmuebleFragment extends Fragment {

    private NuevoInmuebleViewModel mViewModel;
    private FragmentNuevoInmuebleBinding bindingIN;

    public static NuevoInmuebleFragment newInstance() {
        return new NuevoInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        bindingIN=FragmentNuevoInmuebleBinding.inflate(getLayoutInflater());

        bindingIN.btFotoInmuebleNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creo cuadro de dialogo
                new AlertDialog.Builder(requireContext())
                        .setTitle("Seleccione una opción")
                        .setItems(new CharSequence[]{"Tomar foto", "Elegir de la galería","Eliminar foto"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        //tomarFoto();
                                        break;
                                    case 1:
                                        //seleccionarFoto();
                                        break;
                                    case 2:
                                        //eliminarFoto();
                                        break;
                                }
                            }
                        }).setNegativeButton("Cancelar", null).show();               ;
            }
        });

        return bindingIN.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NuevoInmuebleViewModel.class);
        // TODO: Use the ViewModel
    }

}
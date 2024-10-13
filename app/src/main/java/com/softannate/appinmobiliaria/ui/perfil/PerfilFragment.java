package com.softannate.appinmobiliaria.ui.perfil;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.softannate.appinmobiliaria.R;
import com.softannate.appinmobiliaria.databinding.FragmentPerfilBinding;

public class PerfilFragment extends Fragment {

    private PerfilViewModel vmPerfil;
    private FragmentPerfilBinding bindingPerfil;

    public static PerfilFragment newInstance() {
        return new PerfilFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        bindingPerfil = FragmentPerfilBinding.inflate(getLayoutInflater());
        vmPerfil= new ViewModelProvider(this).get(PerfilViewModel.class);

        bindingPerfil.btPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Modificar Password", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.btPass).show();
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);

                navController.navigate(R.id.modificarContraseniaFragment);
            }
        });

        ConstraintLayout layout=bindingPerfil.navPerfil;
        Button btPerfil = bindingPerfil.btPerfil;

        btPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btPerfil.getText().toString().equals("Editar")) {
                    //cambio editText a "editable"
                    for (int i = 0; i < layout.getChildCount(); i++) {// n° hijos
                        View child = layout.getChildAt(i);
                        if (child instanceof EditText) {
                            child.setFocusable(true);
                            child.setFocusableInTouchMode(true);
                        }
                    }
                    btPerfil.setText("Guardar");
                }else if (btPerfil.getText().toString().equals("Guardar")) {
                    for (int i = 0; i < layout.getChildCount(); i++) {
                        View child = layout.getChildAt(i);
                        if (child instanceof EditText) {
                            child.setFocusable(false);
                            child.setFocusableInTouchMode(false);
                        }
                    }
                    btPerfil.setText("Editar");
                }
            }
        });

        bindingPerfil.btFotoPerfil.setOnClickListener(new View.OnClickListener() {
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

        return bindingPerfil.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vmPerfil = new ViewModelProvider(this).get(PerfilViewModel.class);
        // TODO: Use the ViewModel
    }

}
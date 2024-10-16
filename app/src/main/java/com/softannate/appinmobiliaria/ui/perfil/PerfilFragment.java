package com.softannate.appinmobiliaria.ui.perfil;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
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
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.softannate.appinmobiliaria.R;
import com.softannate.appinmobiliaria.databinding.FragmentPerfilBinding;
import com.softannate.appinmobiliaria.modelos.Propietario;

public class PerfilFragment extends Fragment {

    private EditText etDni, etNombre, etApellido, etTelefono, etEmail2;
    private ImageView fotoPerfil;

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


      //inicializo campos
      etApellido=bindingPerfil.etApellido;
      etDni= bindingPerfil.etDni;
      etNombre=bindingPerfil.etNombre;
      etTelefono=bindingPerfil.etTel;
      etEmail2=bindingPerfil.etEmail2;
      fotoPerfil=bindingPerfil.imagenPerfil;

        vmPerfil.leerPropietario();

        //observer propietario
        vmPerfil.getMPropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                if (propietario != null) {
                    etDni.setText(propietario.getDni());
                    etApellido.setText(propietario.getApellido());
                    etEmail2.setText(propietario.getEmail());
                    etNombre.setText(propietario.getNombre());
                    etTelefono.setText(propietario.getTelefono());
                    Glide.with(getContext())
                            .load(propietario.getAvatar())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.drawable.perfil)
                            .into(fotoPerfil);
                }
            }
        });

        // Observador para el botón
        vmPerfil.getButton().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String buttonText) {
                bindingPerfil.btPerfil.setText(buttonText);
            }
        });

        // Observador para el modo de edición
        vmPerfil.getEditar().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isEditing) {
                vmPerfil.cambioEditText(bindingPerfil.navPerfil);
            }
        });

        // cambios entre editar y guardar
        bindingPerfil.btPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Propietario p = new Propietario();
                p.setApellido(etApellido.getText().toString());
                p.setDni(etDni.getText().toString());
                p.setEmail(etEmail2.getText().toString());
                p.setNombre(etNombre.getText().toString());
                p.setTelefono(etTelefono.getText().toString());
                p.setAvatar(p.getAvatar());
                vmPerfil.cambioBoton();
                vmPerfil.update(p);
            }
        });
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
package com.softannate.appinmobiliaria.ui.inmuebles;

import androidx.lifecycle.Observer;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.softannate.appinmobiliaria.R;
import com.softannate.appinmobiliaria.databinding.FragmentDetalleInmuebleBinding;
import com.softannate.appinmobiliaria.modelos.Inmueble;

public class DetalleInmuebleFragment extends Fragment {

    private EditText etTipo, etDireccion, etSuperficie, etPrecio, etLatitud, etLongitud, etAmbientes, etEstado;
    private ImageView fotoInmueble;
    private CheckBox disponible;

    private DetalleInmuebleViewModel vmDetalle;
    private FragmentDetalleInmuebleBinding bindingI;

    public static DetalleInmuebleFragment newInstance() {
        return new DetalleInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        bindingI= FragmentDetalleInmuebleBinding.inflate(getLayoutInflater());


        //inicializo campos
        etAmbientes=bindingI.etAmbientes;
        etDireccion=bindingI.etDire;
        etEstado=bindingI.etEstado;
        etLatitud=bindingI.etLatitud;
        etLongitud=bindingI.etLongitud;
        etPrecio=bindingI.etPrecio;
        etSuperficie=bindingI.etSup;
        etTipo=bindingI.etTipoI;
        fotoInmueble=bindingI.imagenInmuebleDetalle;
        disponible=bindingI.checkBox;

        vmDetalle= ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(DetalleInmuebleViewModel.class);


        vmDetalle.getInmueble().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                etAmbientes.setText(String.valueOf(inmueble.getAmbientes()));
                etDireccion.setText(inmueble.getDireccion());
                etEstado.setText(inmueble.isEstado() ? "Disponible" : "No disponible");
                etLatitud.setText(String.valueOf(inmueble.getLatitud()));
                etLongitud.setText(String.valueOf(inmueble.getLongitud()));
                etPrecio.setText("$"+String.valueOf(inmueble.getPrecio()));
                etSuperficie.setText(String.valueOf(inmueble.getSuperficie())+" m2");
                etTipo.setText(inmueble.getTipo().getDescripcion());
                disponible.setChecked(inmueble.isEstado());
                Glide.with(getContext())
                        .load(inmueble.getFoto())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.perfil)
                        .into(fotoInmueble);

                disponible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        vmDetalle.estado(inmueble);
                    }
                });
            }
        });



        vmDetalle.recuperaInmueble(getArguments());


        bindingI.btFotoInmuebleDetalle.setOnClickListener(new View.OnClickListener() {
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

        return bindingI.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
package com.softannate.appinmobiliaria.ui.contratos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.google.android.material.snackbar.Snackbar;
import com.softannate.appinmobiliaria.R;
import com.softannate.appinmobiliaria.databinding.FragmentDetalleContratoBinding;
import com.softannate.appinmobiliaria.modelos.Contrato;
import com.softannate.appinmobiliaria.request.Utils;

public class DetalleContratoFragment extends Fragment {

    private EditText etInicio, etFin, etInquilino, etInmueble, etPrecio;
    private DetalleContratoViewModel vmContrato;
    private FragmentDetalleContratoBinding bindingDC;

    public static DetalleContratoFragment newInstance() {
        return new DetalleContratoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        bindingDC = FragmentDetalleContratoBinding.inflate(inflater, container, false);
        vmContrato= new ViewModelProvider(this).get(DetalleContratoViewModel.class);

        //inicializo campos
        etFin= bindingDC.etFin;
        etInicio= bindingDC.etInicio;
        etInmueble= bindingDC.etInmueble;
        etInquilino= bindingDC.etInquilino;
        etPrecio= bindingDC.etPrecio;

        //recupero el id del inmueble desde arguments
        vmContrato.leerContrato(getArguments().getInt("inmuebleId", -1));
       // Log.d("inmuebleId", String.valueOf(getArguments().getInt("inmuebleId")));


        //Observadores
        vmContrato.getContrato().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @Override
            public void onChanged(Contrato contrato) {
                etFin.setText(Utils.formatearFecha(String.valueOf(contrato.getHasta())));
                etInicio.setText(Utils.formatearFecha(String.valueOf(contrato.getDesde())));
                etInmueble.setText(String.valueOf(contrato.getInmueble()));
                etInquilino.setText(String.valueOf(contrato.getInquilino()));
                etPrecio.setText(String.valueOf("$ "+contrato.getMonto()));
            }
        });

        bindingDC.btPagos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Registro de los pagos de alquiler", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.btPagos).show();

                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);
                Bundle bundle = new Bundle();
                bundle.putInt("contratoId", vmContrato.getContrato().getValue().getId());
               // Log.d("contratoId", String.valueOf(vmContrato.getContrato().getValue().getId()));
                navController.navigate(R.id.detallePagosFragment, bundle);
            }
        });
        return bindingDC.getRoot();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vmContrato = new ViewModelProvider(this).get(DetalleContratoViewModel.class);
        // TODO: Use the ViewModel
    }

}


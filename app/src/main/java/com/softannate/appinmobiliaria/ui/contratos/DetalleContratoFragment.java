package com.softannate.appinmobiliaria.ui.contratos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.softannate.appinmobiliaria.R;
import com.softannate.appinmobiliaria.databinding.FragmentDetalleContratoBinding;
import com.softannate.appinmobiliaria.databinding.FragmentInmueblesBinding;
import com.softannate.appinmobiliaria.ui.inmuebles.DetalleInmuebleFragment;
import com.softannate.appinmobiliaria.ui.inmuebles.DetalleInmuebleViewModel;

public class DetalleContratoFragment extends Fragment {

    private DetalleInmuebleViewModel mViewModel;
    private FragmentDetalleContratoBinding bindingDC;

    public static DetalleInmuebleFragment newInstance() {
        return new DetalleInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        bindingDC = FragmentDetalleContratoBinding.inflate(inflater, container, false);


        bindingDC.btPagos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Registro de los pagos de alquiler", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.btPagos).show();
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);

                navController.navigate(R.id.detallePagosFragment);
            }
        });
        return bindingDC.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);
        // TODO: Use the ViewModel
    }

}


package com.softannate.appinmobiliaria.ui.inmuebles;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.softannate.appinmobiliaria.R;
import com.softannate.appinmobiliaria.databinding.FragmentInmueblesBinding;

public class InmueblesFragment extends Fragment {

    private InmueblesViewModel vmInmuebles;
    private FragmentInmueblesBinding bindingI;


    public static InmueblesFragment newInstance() {
        return new InmueblesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        bindingI = FragmentInmueblesBinding.inflate(getLayoutInflater());


        bindingI.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Cargue los datos del inmueble a registrar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);

                navController.navigate(R.id.nuevoInmuebleFragment);
            }
        });
        return bindingI.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vmInmuebles = new ViewModelProvider(this).get(InmueblesViewModel.class);
        // TODO: Use the ViewModel
    }

}
package com.softannate.appinmobiliaria.ui.inmuebles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.softannate.appinmobiliaria.R;
import com.softannate.appinmobiliaria.databinding.FragmentInmueblesBinding;
import com.softannate.appinmobiliaria.modelos.InmueblesContratos;
import com.softannate.appinmobiliaria.ui.contratos.ContratosFragment;
import com.softannate.appinmobiliaria.ui.contratos.ContratosViewModel;

import java.util.ArrayList;

public class InmueblesFragment extends Fragment implements AdapterInmueble.OnInmuebleClickListener {

    private InmueblesViewModel vmInmuebles;
    private RecyclerView rvInmuebles;
    private AdapterInmueble adapter;
    private FragmentInmueblesBinding bindingI;

    public static InmueblesFragment newInstance() {
        return new InmueblesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

       bindingI = FragmentInmueblesBinding.inflate(inflater, container, false);
        View root = bindingI.getRoot();

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
        inicializar(root);
        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vmInmuebles = new ViewModelProvider(this).get(InmueblesViewModel.class);
    }
    private void inicializar(View view) {
        rvInmuebles = bindingI.rvInmuebles;
        vmInmuebles = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(InmueblesViewModel.class);
        vmInmuebles.getMInmuebles().observe(getViewLifecycleOwner(), new Observer<ArrayList<InmueblesContratos>>() {

            @Override
            public void onChanged(ArrayList<InmueblesContratos> inmueblesContratoes) {
                GridLayoutManager gl = new GridLayoutManager(requireContext(), 1, GridLayoutManager.VERTICAL, false);
                adapter = new AdapterInmueble(requireContext(), inmueblesContratoes, getLayoutInflater(), InmueblesFragment.this);

                RecyclerView recv = bindingI.rvInmuebles;
                recv.setLayoutManager(gl);
                recv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        vmInmuebles.mostrarInmuebles();
    }
    @Override
    public void onInmuebleClick(InmueblesContratos inmueble) {
        // Aquí manejo la navegación a la vista de detalles del contrato
        Bundle bundle = new Bundle();
        bundle.putSerializable("inmueble", inmueble.getInmueble());
        Navigation.findNavController(requireView()).navigate(R.id.detalleInmuebleFragment, bundle);
    }
}

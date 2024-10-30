package com.softannate.appinmobiliaria.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.softannate.appinmobiliaria.R;
import com.softannate.appinmobiliaria.databinding.FragmentContratosBinding;
import com.softannate.appinmobiliaria.modelos.InmueblesContratos;
import com.softannate.appinmobiliaria.ui.inmuebles.AdapterInmueble;

import java.util.ArrayList;

public class ContratosFragment extends Fragment implements AdapterInmueble.OnInmuebleClickListener {

    private ContratosViewModel vmCInmueble;
    private FragmentContratosBinding bindingCo;
    private RecyclerView rvCInmuebles;
    private AdapterInmueble adapter;

    public static ContratosFragment newInstance() {
        return new ContratosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        bindingCo = FragmentContratosBinding.inflate(inflater, container, false);
        View root = bindingCo.getRoot();

        inicializar(root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vmCInmueble = new ViewModelProvider(this).get(ContratosViewModel.class);
        // TODO: Use the ViewModel
    }

    private void inicializar(View view) {
        rvCInmuebles = bindingCo.rvContrato;
        vmCInmueble = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(ContratosViewModel.class);
        vmCInmueble.getMInmuebles().observe(getViewLifecycleOwner(), new Observer<ArrayList<InmueblesContratos>>() {

            @Override
            public void onChanged(ArrayList<InmueblesContratos> inmueblesContratoes) {
                GridLayoutManager gl = new GridLayoutManager(requireContext(), 1, GridLayoutManager.VERTICAL, false);
                adapter = new AdapterInmueble(requireContext(), inmueblesContratoes, getLayoutInflater(), ContratosFragment.this);

                RecyclerView recv = bindingCo.rvContrato;
                recv.setLayoutManager(gl);
                recv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        vmCInmueble.mostrarAlquilados();
    }

    @Override
    public void onInmuebleClick(InmueblesContratos ic) {
        //navegación a la vista de detalles del contrato y envía el ID de inmueble
        Bundle bundleC = new Bundle();
        bundleC.putInt("inmuebleId", ic.getInmueble().getId());
        Log.d("inmueble", "ID del inmueble: " + ic.getInmueble().getId());
        Navigation.findNavController(requireView()).navigate(R.id.detalleContratoFragment, bundleC);
    }
}
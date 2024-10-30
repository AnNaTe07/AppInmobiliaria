package com.softannate.appinmobiliaria.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.softannate.appinmobiliaria.R;
import com.softannate.appinmobiliaria.databinding.FragmentDetallePagosBinding;
import com.softannate.appinmobiliaria.modelos.Pago;

import java.util.ArrayList;

public class DetallePagosFragment extends Fragment {

    private DetallePagosViewModel vmPago;
    private FragmentDetallePagosBinding bindingP;
    private RecyclerView rvPago;
    private AdapterPago adapter;

    public static DetallePagosFragment newInstance() {
        return new DetallePagosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        bindingP= FragmentDetallePagosBinding.inflate(inflater, container, false);
        View root= bindingP.getRoot();
        inicializar(root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vmPago = new ViewModelProvider(this).get(DetallePagosViewModel.class);

        // TODO: Use the ViewModel
    }

    private void inicializar(View view){
        rvPago=bindingP.rvPagos;
        vmPago=ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(DetallePagosViewModel.class);
        vmPago.getMPago().observe(getViewLifecycleOwner(), new Observer<ArrayList<Pago>>() {

            @Override
            public void onChanged(ArrayList<Pago> pagos) {
                if (pagos != null && !pagos.isEmpty()) {
                    GridLayoutManager gl = new GridLayoutManager(requireContext(), 1, GridLayoutManager.VERTICAL, false);
                    adapter = new AdapterPago(requireContext(), pagos, getLayoutInflater());

                    rvPago.setLayoutManager(gl);
                    rvPago.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d("DetallePagosFragment", "No hay pagos disponibles.");
                    Toast.makeText(getContext(), "No hay pagos disponibles", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //recupero el id del contrato desde arguments
        vmPago.leerPagos(getArguments().getInt("contratoId", -1));
       // Log.d("contratoId", String.valueOf(getArguments().getInt("contratoId")));
    }

}
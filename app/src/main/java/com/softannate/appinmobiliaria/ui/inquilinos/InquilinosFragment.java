package com.softannate.appinmobiliaria.ui.inquilinos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softannate.appinmobiliaria.R;
import com.softannate.appinmobiliaria.databinding.FragmentInquilinosBinding;
import com.softannate.appinmobiliaria.modelos.InmueblesContratos;
import com.softannate.appinmobiliaria.ui.inmuebles.AdapterInmueble;

import java.util.ArrayList;

public class InquilinosFragment extends Fragment implements AdapterInmueble.OnInmuebleClickListener {

    private InquilinosViewModel vmInquilinos;
    private FragmentInquilinosBinding bindingIn;
    private RecyclerView rvInq;
    private AdapterInmueble adapter;

    public static InquilinosFragment newInstance() {
        return new InquilinosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        bindingIn = FragmentInquilinosBinding.inflate(inflater, container, false);
        View root = bindingIn.getRoot();
        inicializar(root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vmInquilinos = new ViewModelProvider(this).get(InquilinosViewModel.class);
        // TODO: Use the ViewModel
    }
    private void inicializar(View view){
        rvInq =bindingIn.rvInquilino;
        vmInquilinos=ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(InquilinosViewModel.class);
        vmInquilinos.getMInmueble().observe(getViewLifecycleOwner(), new Observer<ArrayList<InmueblesContratos>>(){

            @Override
            public void onChanged(ArrayList<InmueblesContratos> inmuebles) {
                GridLayoutManager gl=new GridLayoutManager(requireContext(),1,GridLayoutManager.VERTICAL,false);
                adapter = new AdapterInmueble(requireContext(),inmuebles,getLayoutInflater(), InquilinosFragment.this);

                RecyclerView recv= bindingIn.rvInquilino;
                recv.setLayoutManager(gl);
                recv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        vmInquilinos.mostrarAlquilados();
    }
    @Override
    public void onInmuebleClick(InmueblesContratos ic) {
        //manejo la navegación a la vista de detalles del inquilino y envío el id del inquilino
        Bundle bundleIn = new Bundle();
        bundleIn.putInt("inquilinoId", ic.getContrato().getInqui().getId());
        Navigation.findNavController(requireView()).navigate(R.id.detalleInquilinoFragment, bundleIn);
    }
}
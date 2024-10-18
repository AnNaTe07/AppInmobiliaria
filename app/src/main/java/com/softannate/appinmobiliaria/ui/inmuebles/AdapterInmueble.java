package com.softannate.appinmobiliaria.ui.inmuebles;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.softannate.appinmobiliaria.R;
import com.softannate.appinmobiliaria.modelos.Inmueble;

import java.util.ArrayList;

public class AdapterInmueble extends RecyclerView.Adapter<AdapterInmueble.ViewHolderInmueble> {

    private Context contexto;
    private ArrayList<Inmueble> inmuebles;
    private LayoutInflater inflater;

    public AdapterInmueble(Context contexto, ArrayList<Inmueble> inmuebles, LayoutInflater inflater) {
        this.contexto = contexto;
        this.inmuebles = inmuebles;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolderInmueble onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_inmueble, parent, false);
        return new ViewHolderInmueble(view);
    }

    //seteo datos a c/inmueble
    @Override
    public void onBindViewHolder(@NonNull ViewHolderInmueble holder, int position) {

        Inmueble inmueble = inmuebles.get(position);
        holder.tvTipo.setText(inmueble.getTipo().getDescripcion());
        holder.tvDir.setText(inmueble.getDireccion());
        holder.tvAmb.setText(String.valueOf(inmueble.getAmbientes())+ " ambientes");
        holder.tvPrecio.setText("$ "+String.valueOf(inmueble.getPrecio()));
        Glide.with(contexto)
                .load(inmueble.getFoto())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.perfil)
                .into((ImageView)holder.ivInmueble);
    }

    @Override
    public int getItemCount() {
        return inmuebles.size();
    }

    public class ViewHolderInmueble extends RecyclerView.ViewHolder {

        private TextView tvTipo, tvDir, tvAmb, tvPrecio;
        private View ivInmueble;

        public ViewHolderInmueble(@NonNull View itemView) {
            super(itemView);
            tvTipo = itemView.findViewById(R.id.tvTipo);
            tvDir = itemView.findViewById(R.id.tvDir);
            tvAmb = itemView.findViewById(R.id.tvAmb);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            ivInmueble = itemView.findViewById(R.id.ivInmueble);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    Inmueble inmueble= inmuebles.get(getAdapterPosition());
                    bundle.putSerializable("inmueble", inmueble);
                    Navigation.findNavController(view).navigate(R.id.detalleInmuebleFragment, bundle);
                }
            });

        }
    }
}




package com.softannate.appinmobiliaria.ui.inmuebles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.softannate.appinmobiliaria.R;
import com.softannate.appinmobiliaria.modelos.Inmueble;
import com.softannate.appinmobiliaria.modelos.InmueblesContratos;

import java.util.ArrayList;

public class AdapterInmueble extends RecyclerView.Adapter<AdapterInmueble.ViewHolderInmueble> {

    private Context contexto;
    private ArrayList<InmueblesContratos> inmueblesContratoes;
    private LayoutInflater inflater;
    private OnInmuebleClickListener listener;


    public AdapterInmueble(Context contexto, ArrayList<InmueblesContratos> inmueblesContratoes, LayoutInflater inflater, OnInmuebleClickListener listener) {
        this.contexto = contexto;
        this.inmueblesContratoes = inmueblesContratoes;
        this.inflater = inflater;
        this.listener = listener;
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
        InmueblesContratos ic = inmueblesContratoes.get(position);
        Inmueble inmueble = ic.getInmueble();

        // Verifico si inmueble y su tipo son nulos
        if (inmueble != null && inmueble.getTipo() != null) {
            holder.tvTipo.setText(inmueble.getTipo().getDescripcion());
        } else {
            holder.tvTipo.setText("Tipo desconocido");
        }
        holder.tvDir.setText(inmueble != null ? inmueble.getDireccion() : "Dirección desconocida");
        holder.tvAmb.setText(inmueble != null ? inmueble.getAmbientes() + " ambientes" : "0 ambientes");
        holder.tvPrecio.setText(inmueble != null ? "$ " + inmueble.getPrecio() : "$ 0");
        Glide.with(contexto)
                .load(inmueble != null ? inmueble.getFoto() : R.drawable.perfil)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.casa)
                .into((ImageView) holder.ivInmueble);
    }

    @Override
    public int getItemCount() {
        return inmueblesContratoes.size();
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
                    InmueblesContratos ic = inmueblesContratoes.get(getAdapterPosition());
                    listener.onInmuebleClick(ic);
                }
            });
        }
    }
    public interface OnInmuebleClickListener {
        void onInmuebleClick(InmueblesContratos inmueblesContratos);

    }
}




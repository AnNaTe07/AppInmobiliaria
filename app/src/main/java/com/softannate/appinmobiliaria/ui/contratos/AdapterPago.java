package com.softannate.appinmobiliaria.ui.contratos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softannate.appinmobiliaria.R;
import com.softannate.appinmobiliaria.modelos.Inmueble;
import com.softannate.appinmobiliaria.modelos.Pago;

import java.util.ArrayList;
import java.util.List;

public class AdapterPago extends RecyclerView.Adapter<AdapterPago.ViewHolderPago> {

    private Context context;
    private ArrayList<Pago> pagos;
    private LayoutInflater inflater;

    public AdapterPago(Context context, ArrayList<Pago> pagos, LayoutInflater inflater) {
        this.context = context;
        this.pagos = pagos;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolderPago onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_pago, parent, false);
        return new ViewHolderPago(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPago holder, int position) {

        Pago pago= pagos.get(position);
        holder.tvNro.setText(String.valueOf(pago.getNro()));
        holder.tvFecha.setText(pago.getFecha().toString());
        holder.tvImporte.setText("$ "+String.valueOf(pago.getMonto()));
        holder.tvDomicilio.setText(pago.getContrato().getInmu().getDireccion());
        holder.tvCodigo.setText(String.valueOf(pago.getId()));
    }

    @Override
    public int getItemCount() {
        return pagos.size();
    }

    public class ViewHolderPago extends RecyclerView.ViewHolder {

        private TextView tvNro, tvFecha, tvImporte, tvDomicilio, tvCodigo;

        public ViewHolderPago(@NonNull View itemView) {
            super(itemView);
            tvNro = itemView.findViewById(R.id.tvNro);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvImporte = itemView.findViewById(R.id.tvImporte);
            tvDomicilio = itemView.findViewById(R.id.tvDomicilio);
            tvCodigo = itemView.findViewById(R.id.tvCodigo);
        }
    }
}

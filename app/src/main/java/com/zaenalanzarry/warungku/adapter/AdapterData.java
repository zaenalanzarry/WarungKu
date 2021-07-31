package com.zaenalanzarry.warungku.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zaenalanzarry.warungku.R;
import com.zaenalanzarry.warungku.model.modelApp;

import java.util.ArrayList;

public class AdapterData extends RecyclerView.Adapter<AdapterData.MyViewHolder> {

    Context context;

    ArrayList<modelApp> list;

    public AdapterData(Context context, ArrayList<modelApp> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.data_produk, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        modelApp model = list.get(position);
        holder.namaBarang.setText(model.getNamaBarang());
        holder.hargaBeli.setText(model.getHargaBeli());
        holder.hargaJual.setText(model.getHargaJual());
        holder.stok.setText(model.getStok());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView namaBarang, hargaBeli, hargaJual, stok;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            namaBarang = itemView.findViewById(R.id.namaBarang);
            hargaBeli = itemView.findViewById(R.id.hargaBeli);
            hargaJual = itemView.findViewById(R.id.hargaJual);
            stok = itemView.findViewById(R.id.stok);
        }
    }
}

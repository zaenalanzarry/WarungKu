package com.zaenalanzarry.warungku.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zaenalanzarry.warungku.R;
import com.zaenalanzarry.warungku.crud.TambahData;
import com.zaenalanzarry.warungku.model.modelApp;

import java.util.List;

public class AdapterData extends RecyclerView.Adapter<AdapterData.MyViewHolder> {

    private List<modelApp> list;
    private Activity activity;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String userId = fAuth.getCurrentUser().getUid();
    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users").child(userId);

    public AdapterData(List<modelApp> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.data_produk, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterData.MyViewHolder holder, int position) {
        modelApp model = list.get(position);
        holder.tv_namaBarang.setText("Nama Barang : "+model.getNamaBarang());
        holder.tv_hargaBeli.setText("Harga Beli : "+model.getHargaBeli());
        holder.tv_hargaJual.setText("Harga Jual : "+model.getHargaJual());
        holder.tv_stok.setText("Stok : "+model.getStok());

        holder.btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        database.child("Data").child(model.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(activity, "Berhasil dihapus", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull  Exception e) {
                                Toast.makeText(activity, "Gagal dihapus", Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                }).setMessage("Apakah yakin ingin dihapus "+ model.getNamaBarang()+" ?");
                builder.show();
            }
        });

        holder.card_view_rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
                TambahData tambahData = new TambahData(model.getNamaBarang(),
                        model.getHargaBeli(),
                        model.getHargaJual(),
                        model.getStok(),
                        model.getKey(),
                        "Ubah");

                tambahData.show(manager, "form");
            }

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_namaBarang, tv_hargaBeli, tv_hargaJual, tv_stok;

        CardView card_view_rv;

        ImageView btn_hapus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_namaBarang = itemView.findViewById(R.id.tv_namaBarang);
            tv_hargaBeli = itemView.findViewById(R.id.tv_hargaBeli);
            tv_hargaJual = itemView.findViewById(R.id.tv_hargaJual);
            tv_stok = itemView.findViewById(R.id.tv_stok);

            btn_hapus = itemView.findViewById(R.id.btn_hapus);
            card_view_rv = itemView.findViewById(R.id.card_view_rv);
        }
    }
}

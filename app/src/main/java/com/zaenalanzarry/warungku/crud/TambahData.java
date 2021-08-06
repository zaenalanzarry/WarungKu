package com.zaenalanzarry.warungku.crud;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zaenalanzarry.warungku.R;
import com.zaenalanzarry.warungku.model.modelApp;

public class TambahData extends DialogFragment {

    String namaBarang, hargaBeli, hargaJual, stok, key, pilih;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public TambahData(String namaBarang, String hargaBeli, String hargaJual, String stok, String key, String pilih) {
        this.namaBarang = namaBarang;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
        this.stok = stok;
        this.key = key;
        this.pilih = pilih;
    }

    TextView et_namaBarang, et_hargaBeli, et_hargaJual, et_stok;
    Button btn_tambah;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.activity_form_tambah_data, container, false);

        et_namaBarang = view.findViewById(R.id.et_namaBarang);
        et_hargaBeli = view.findViewById(R.id.et_hargaBeli);
        et_hargaJual = view.findViewById(R.id.et_hargaJual);
        et_stok = view.findViewById(R.id.et_stok);

        et_namaBarang.setText(namaBarang);
        et_hargaBeli.setText(hargaBeli);
        et_hargaJual.setText(hargaJual);
        et_stok.setText(stok);

        btn_tambah = view.findViewById(R.id.btn_tambah);

        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaBarang = et_namaBarang.getText().toString();
                String hargaBeli = et_hargaBeli.getText().toString();
                String hargaJual = et_hargaJual.getText().toString();
                String stok = et_stok.getText().toString();

                if(TextUtils.isEmpty(namaBarang)){
                    input((EditText) et_namaBarang, "Nama Barang");
                } else if(TextUtils.isEmpty(hargaBeli)){
                    input((EditText) et_hargaBeli, "Harga Beli");
                } else if(TextUtils.isEmpty(hargaJual)){
                    input((EditText) et_hargaJual, "Harga Jual");
                } else if(TextUtils.isEmpty(stok)){
                    input((EditText) et_stok, "Stok");
                }  else {
                    if(pilih.equals("Tambah")){
                        database.child("Data").push().setValue(new modelApp(namaBarang, hargaBeli, hargaJual, stok))
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(view.getContext(), "Data Berhasil Tersimpan", Toast.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Data Gagal Tersimpan", Toast.LENGTH_LONG).show();
                            }
                        });

                    } else if(pilih.equals("Ubah")){
                        database.child("Data").child(key).setValue(new modelApp(namaBarang, hargaBeli, hargaJual, stok))
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(view.getContext(), "Data Berhasil Diubah", Toast.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Data Gagal Diubah", Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                }
            }
        });

        return view;
    }

    public void onStart() {

        super.onStart();
        Dialog dialog = getDialog();
        if(dialog != null){
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

    }

    private void input (EditText txt, String s){
        txt.setError(s+ " tidak boleh kosong");
        txt.requestFocus();
        return;
    }
}

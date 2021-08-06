package com.zaenalanzarry.warungku.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zaenalanzarry.warungku.R;
import com.zaenalanzarry.warungku.adapter.AdapterData;
import com.zaenalanzarry.warungku.crud.TambahData;
import com.zaenalanzarry.warungku.model.modelApp;


import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Data#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Data extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Data() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Data.
     */
    // TODO: Rename and change types and number of parameters
    public static Data newInstance(String param1, String param2) {
        Data fragment = new Data();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FloatingActionButton fab_tambah;
    AdapterData recyclerAdapter;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    ArrayList<modelApp> listBarang;
    RecyclerView rv_data;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_data, container, false);

        fab_tambah = view.findViewById(R.id.fab_tambah);
        rv_data = view.findViewById(R.id.rv_data);

        RecyclerView.LayoutManager mLayout = new LinearLayoutManager(getActivity());
        rv_data.setLayoutManager(mLayout);
        rv_data.setItemAnimator(new DefaultItemAnimator());

        fab_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TambahData tambahData = new TambahData("","","","","","Tambah");
                tambahData.show(getActivity().getSupportFragmentManager(), "form");

            }
        });

        showData();

        return view;

    }

    private void showData(){
        database.child("Data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listBarang = new ArrayList<>();
                for(DataSnapshot item : snapshot.getChildren()){
                    modelApp tmbh = item.getValue(modelApp.class);
                    tmbh.setKey(item.getKey());
                    listBarang.add(tmbh);
                }

                recyclerAdapter = new AdapterData(listBarang, getActivity());
                rv_data.setAdapter(recyclerAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
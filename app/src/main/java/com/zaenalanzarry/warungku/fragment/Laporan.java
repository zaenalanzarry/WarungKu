package com.zaenalanzarry.warungku.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zaenalanzarry.warungku.R;
import com.zaenalanzarry.warungku.model.modelChart;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Laporan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Laporan extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Laporan() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Laporan.
     */
    // TODO: Rename and change types and number of parameters
    public static Laporan newInstance(String param1, String param2) {
        Laporan fragment = new Laporan();
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

    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Data");
    AnyChartView anyChartView;
    ArrayList<modelChart> listaja;
    String[] barang = {"aa", "bb"};
    int[] stok = {1,2};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_laporan, container, false);

        anyChartView = view.findViewById(R.id.piechart);

        setUpChartView();

        return view;
    }

   private void setUpChartView() {

       Pie pie = AnyChart.pie();
       pie.background("black");

       List<DataEntry> dataEntries = new ArrayList<>();

       for (int i=0; i<barang.length; i++){
           dataEntries.add(new ValueDataEntry(barang[i], stok[i]));
       }

       pie.data(dataEntries);
       pie.title("Gaji");
       anyChartView.setChart(pie);

    }

}
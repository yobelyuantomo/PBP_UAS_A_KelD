package com.kelompokd.pbp_uts_a_keld.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kelompokd.pbp_uts_a_keld.Car;
import com.kelompokd.pbp_uts_a_keld.CarList;
import com.kelompokd.pbp_uts_a_keld.CarRecyclerViewAdapter;
import com.kelompokd.pbp_uts_a_keld.R;

import java.util.ArrayList;

public class SewaFragment extends Fragment {
    private ArrayList<Car> listCar;
    private RecyclerView recyclerView;
    private CarRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_sewa, container, false);

        ImageButton back_btn = root.findViewById(R.id.back_btn);
        ImageButton acc_setting_corner = root.findViewById(R.id.account_settings_corner);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.nav_home);
            }
        });

        acc_setting_corner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.nav_acc_settings);
            }
        });

        //get data mahasiswa
        listCar = new CarList().CAR;

        //recycler view
        recyclerView = root.findViewById(R.id.recycler_car_list);
        adapter = new CarRecyclerViewAdapter(getContext(), listCar);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        return root;
    }
}
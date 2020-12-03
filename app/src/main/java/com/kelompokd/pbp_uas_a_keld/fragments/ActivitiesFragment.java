package com.kelompokd.pbp_uas_a_keld.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kelompokd.pbp_uas_a_keld.R;

public class ActivitiesFragment extends Fragment {

    private BottomNavigationView mBottomNav;
    private int mSelectedItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_activities, container, false);

        ImageButton back_btn = root.findViewById(R.id.back_btn);
        ImageButton acc_setting_corner = root.findViewById(R.id.account_settings_corner);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.nav_home);
            }
        });

        CardView sewa_activities = root.findViewById(R.id.sewa_activities);
        sewa_activities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.nav_sewa_mobil);
            }
        });

        CardView wisata_activities = root.findViewById(R.id.wisata_activities);
        wisata_activities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.nav_paketWisata);
            }
        });

        acc_setting_corner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.nav_acc_settings);
            }
        });

        CardView wishlist = root.findViewById(R.id.cv_wishlist);
        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.nav_wishlist);
            }
        });

        CardView lihatPesanan = root.findViewById(R.id.cv_lihatPesanan);
        lihatPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.nav_lihatPesanan);
            }
        });
        return root;
    }

}
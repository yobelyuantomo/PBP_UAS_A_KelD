package com.kelompokd.pbp_uas_a_keld.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.kelompokd.pbp_uas_a_keld.R;

public class AboutusFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_aboutus, container, false);

        CardView our_teams = root.findViewById(R.id.our_teams);
        our_teams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.nav_our_teams);
            }
        });

        ImageButton acc_setting_corner = root.findViewById(R.id.account_settings_corner);
        acc_setting_corner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.nav_acc_settings);
            }
        });

        ImageButton back_btn = root.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.nav_home);
            }
        });

        CardView our_location = root.findViewById(R.id.our_locations);
        our_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.nav_our_location);
            }
        });

        return root;
    }
}
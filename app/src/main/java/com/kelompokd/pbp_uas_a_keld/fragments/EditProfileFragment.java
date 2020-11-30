package com.kelompokd.pbp_uas_a_keld.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.kelompokd.pbp_uas_a_keld.R;

public class EditProfileFragment extends Fragment {

    MaterialButton btnBatal, btnSimpan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        btnBatal = root.findViewById(R.id.btnBatal);
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.nav_acc_settings);
            }
        });

        btnSimpan = root.findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Nanti kalau sudah selesai mengedit data dan menekan button simpan, data akan tersimpan ke database dengan Volley Laravel API
            }
        });

        return root;
    }
}
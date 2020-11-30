package com.kelompokd.pbp_uas_a_keld.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelompokd.pbp_uas_a_keld.R;

public class PesananFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        /*
            Lihat xml, ada note di xml nya, Selain note yang ada di xml, nanti pada saat item pada adapter
            di klik, akan masuk ke fragment hapus atau edit (Fragment PreviewPesanan) (Bener nya bisa dua pilihan, mau kayak modul sebelumnya
            yang bisa di slide trus ada pilihan hapus/edit atau masuk fragment baru) <- ini mau pakai fragment PreviewPesanan yang aku buat atau mau kyk GD
            sebelunya terserah.


            Note: adapter nya buat sendiri ya
         */
        return inflater.inflate(R.layout.fragment_pesanan, container, false);
    }
}
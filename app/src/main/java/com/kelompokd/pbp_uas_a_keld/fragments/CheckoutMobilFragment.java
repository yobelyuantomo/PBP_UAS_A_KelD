package com.kelompokd.pbp_uas_a_keld.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;
import com.kelompokd.pbp_uas_a_keld.R;

public class CheckoutMobilFragment extends Fragment {

    MaterialButton btnBatal, btnCheckout;

    SharedPreferences sharedpreferences;
    public static final String loginPreferences = "loginPreferences";
    String id, nama, email;

    public final static String TAG_ID = "id";
    public final static String TAG_FULL_NAME = "full_name";
    public final static String TAG_EMAIL = "email";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_checkout_mobil, container, false);

        //Ambil data user dari data yang disimpan pada sharedpreferences
        sharedpreferences = getActivity().getSharedPreferences(loginPreferences, Context.MODE_PRIVATE);
        id = sharedpreferences.getString(TAG_ID, null);
        nama = sharedpreferences.getString(TAG_FULL_NAME, null);
        email = sharedpreferences.getString(TAG_EMAIL, null);

        ImageButton acc_setting_corner = root.findViewById(R.id.account_settings_corner);
        acc_setting_corner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.nav_acc_settings);
            }
        });

        btnBatal = root.findViewById(R.id.btnBatal);
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.sewa_activities);
            }
        });

        btnCheckout = root.findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                    Setelah menekan button checkout, user akan diarahkan ke fragment pesanan, nanti pada fragment pesanan
                    tampilkan data dengan adapter. Setelah tombol btn checkout, program juga akan mengirim data ke database
                    menggunakan Volley Laravel API

                    untuk data apa saja yang harus dikirim, juga harus mempertimbangkan data yang ada di preview pesanan

                    Data alamat dll, ambil dari database ya, Id nya bisa ambil pakai sharedpreferences
                 */
            }
        });

        return root;
    }
}
package com.kelompokd.pbp_uas_a_keld.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelompokd.pbp_uas_a_keld.R;

public class LihatSewaMobilFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lihat_sewa_mobil, container, false);

        return view;
    }
}
package com.kelompokd.pbp_uas_a_keld.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.kelompokd.pbp_uas_a_keld.fragments.CRUDmobil.LihatPesananMobil;
import com.kelompokd.pbp_uas_a_keld.fragments.LihatPesananWisata;

public class PageAdapter extends FragmentStatePagerAdapter {
    private int number_tabs;

    public PageAdapter(@NonNull FragmentManager fm, int number_tabs) {
        super(fm);
        this.number_tabs = number_tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new LihatPesananMobil();
            case 1:
                return new LihatPesananWisata();
            default:
                return null;
        }
    }

    @Override
    public int getCount()
    {
        return number_tabs;
    }
}
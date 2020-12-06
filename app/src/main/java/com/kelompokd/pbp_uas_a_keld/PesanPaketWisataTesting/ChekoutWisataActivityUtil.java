package com.kelompokd.pbp_uas_a_keld.PesanPaketWisataTesting;

import android.content.Context;
import android.content.Intent;

import com.kelompokd.pbp_uas_a_keld.HomeActivity;
import com.kelompokd.pbp_uas_a_keld.fragments.LihatPesananFragment;

public class ChekoutWisataActivityUtil {
    private Context context;

    public ChekoutWisataActivityUtil(Context context) {
        this.context = context;
    }

    public void startCheckoutWisataFragment() {
        context.startActivity(new Intent(context, LihatPesananFragment.class));
    }

}
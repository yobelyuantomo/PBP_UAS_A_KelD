package com.kelompokd.pbp_uas_a_keld.UnitTest;

import android.content.Context;
import android.content.Intent;

import com.kelompokd.pbp_uas_a_keld.HomeActivity;

public class ActivityUtil {
    private Context context;

    public ActivityUtil(Context context) {
        this.context = context;
    }

    public void startMainActivity() {
        context.startActivity(new Intent(context, HomeActivity.class));
    }

}
package com.kelompokd.pbp_uas_a_keld.UnitTestLogin;

import android.content.Context;
import android.content.Intent;

import com.kelompokd.pbp_uas_a_keld.HomeActivity;

public class LoginActivityUtil {
    private Context context;

    public LoginActivityUtil(Context context) {
        this.context = context;
    }

    public void startMainActivity() {
        context.startActivity(new Intent(context, HomeActivity.class));
    }

}
package com.kelompokd.pbp_uts_a_keld.anim;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.kelompokd.pbp_uts_a_keld.HomeActivity;
import com.kelompokd.pbp_uts_a_keld.LoginActivity;
import com.kelompokd.pbp_uts_a_keld.R;

public class SplashScreen extends AppCompatActivity {

    //gambar
    ImageView logo_moRent;

    //Animation
    Animation animation_moRent;

    // Pindah Main 4000 = 4s
    private static int SPLASH_TIME_OUT = 4000;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("night",0);
        Boolean booleanValue = sharedPreferences.getBoolean("night_mode",true);
        if (booleanValue){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        // Animation Utils
        animation_moRent = AnimationUtils.loadAnimation(this,R.anim.splash_animation);


        //FindById untuk gambar
        logo_moRent = findViewById(R.id.moRent_logo);

        // Set Animasi Untuk gambar
        logo_moRent.setAnimation(animation_moRent);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashScreen.this, LoginActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(mainIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}

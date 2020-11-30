package com.kelompokd.pbp_uas_a_keld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    TextView txt_nama, txt_nim, txt_email;
    Button btn_logout;

    String id, nama, email;
    SharedPreferences sharedpreferences;

    public final static String TAG_ID = "id";
    public final static String TAG_FULL_NAME = "full_name";
    public final static String TAG_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        txt_nama = findViewById(R.id.txt_nama_dashboard);
        txt_email = findViewById(R.id.txt_email_dashboard);
        btn_logout = findViewById(R.id.btn_logout);

        //session
        sharedpreferences = getSharedPreferences(LoginActivity.loginPreferences, Context.MODE_PRIVATE);

        id = getIntent().getStringExtra(TAG_ID);
        nama = getIntent().getStringExtra(TAG_FULL_NAME);
        email = getIntent().getStringExtra(TAG_EMAIL);

        //setText
        txt_nama.setText(": "+nama);
        txt_email.setText(": "+email);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // update login session ke FALSE dan mengosongkan nilai id dan username
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(LoginActivity.session_status, false);
                editor.putString(TAG_ID, null);
                editor.putString(TAG_FULL_NAME, null);
                editor.putString(TAG_EMAIL, null);
                editor.commit();

                Intent ua = new Intent(DashboardActivity.this, LoginActivity.class);
                ua.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(ua);

            }
        });
    }
}
package com.kelompokd.pbp_uas_a_keld;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.kelompokd.pbp_uas_a_keld.API.MorentAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;

public class VerifActivity extends AppCompatActivity {

    MaterialButton btnGmail;
    TextView resend;

    ImageButton btn_refresh;

    String id, nama, email;
    SharedPreferences sharedpreferences;

    public static final String loginPreferences = "loginPreferences";
    public static final String session_status = "session_status";
    public static final String verify_status = "verify_status";

    public final static String TAG_ID = "id";
    public final static String TAG_FULL_NAME = "full_name";
    public final static String TAG_EMAIL = "email";

    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verif);

        // Cek session login jika TRUE maka langsung buka Dashbard
        sharedpreferences = getSharedPreferences(loginPreferences, Context.MODE_PRIVATE);
        id = sharedpreferences.getString(TAG_ID, null);
        nama = sharedpreferences.getString(TAG_FULL_NAME, null);
        email = sharedpreferences.getString(TAG_EMAIL, null);

        btnGmail = findViewById(R.id.btnGmail);
        resend = findViewById(R.id.resend);
        btn_refresh = findViewById(R.id.btn_refresh);

        btnGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mailClient = new Intent(Intent.ACTION_VIEW);
                mailClient.setClassName("com.google.android.gm", "com.google.android.gm.ConversationListActivity");
                startActivity(mailClient);
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pendeklarasian queue
                RequestQueue queue = Volley.newRequestQueue(VerifActivity.this);
                final ProgressDialog progressDialog;
                progressDialog = new ProgressDialog(VerifActivity.this);
                progressDialog.setMessage("loading....");
                progressDialog.setTitle("Resend");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();

                //Memulai membuat permintaan request menghapus data ke jaringan
                StringRequest stringRequest = new StringRequest(GET, MorentAPI.URL_RESEND + id, new
                        Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                                progressDialog.dismiss();
                                try {
                                    //Mengubah response string menjadi object
                                    JSONObject jsonObject = new JSONObject(response);

                                    Toast.makeText(getApplicationContext(), jsonObject.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Disini bagian jika response jaringan terdapat ganguan/error
                        progressDialog.dismiss();
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null && networkResponse.data != null) {
                            String jsonError = new String(networkResponse.data);

                            try {
                                //Mengubah response string menjadi object
                                JSONObject jsonObject = new JSONObject(jsonError);
                                String status = jsonObject.getString("status");
                                String message = jsonObject.getString("message");

                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(retryPolicy);

                // Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
                queue.add(stringRequest);
            }
        });

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin(email);
            }
        });
    }

    //Fungsi menampilkan data mahasiswa
    public void checkLogin(String email) {
        //Pendeklarasian queue
        RequestQueue queue = Volley.newRequestQueue(VerifActivity.this);
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(VerifActivity.this);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Check your credentials");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Memulai membuat permintaan request menghapus data ke jaringan
        StringRequest stringRequest = new StringRequest(POST, MorentAPI.URL_REFRESH, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                        progressDialog.dismiss();
                        try {
                            //Mengubah response string menjadi object
                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getString("message").equals("Verify Success"))
                            {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                                // menyimpan login ke session
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putBoolean(session_status, true);
                                editor.putBoolean(verify_status, true);
                                editor.putString(TAG_ID, id);
                                editor.putString(TAG_FULL_NAME, nama);
                                editor.putString(TAG_EMAIL, email);
                                editor.commit();

                                // Memanggil Home
                                Intent intent = new Intent(VerifActivity.this, HomeActivity.class);
                                intent.putExtra(TAG_ID, id);
                                intent.putExtra(TAG_FULL_NAME, nama);
                                intent.putExtra(TAG_EMAIL, email);
                                finish();
                                startActivity(intent);
                            }
//                            else {
//
//                                Toast.makeText(VerifActivity.this, jsonObject.getString("message") , Toast.LENGTH_SHORT).show();
//
//                                // Memanggil VerifActivity
//                                Intent intent = new Intent(VerifActivity.this, VerifActivity.class);
//                                finish();
//                                startActivity(intent);
//                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Disini bagian jika response jaringan terdapat ganguan/error
                progressDialog.dismiss();
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.data != null) {
                    String jsonError = new String(networkResponse.data);

                    try {
                        //Mengubah response string menjadi object
                        JSONObject jsonObject = new JSONObject(jsonError);
                        String status = jsonObject.getString("status");
                        String message = jsonObject.getString("message");

                        if(status.equals("verify")){
                            Toast.makeText(VerifActivity.this, message , Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                     /*
                     Disini adalah proses memasukan/mengirimkan parameter key dengan data
                    value, dan nama key nya harus sesuai dengan parameter key yang diminta
                    oleh jaringan API.
                     */
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                return params;
            }
        };
        // Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }
}
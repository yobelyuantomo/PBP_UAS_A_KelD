package com.kelompokd.pbp_uas_a_keld;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textview.MaterialTextView;
import com.kelompokd.pbp_uas_a_keld.API.MorentAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class LoginActivity extends AppCompatActivity {

    EditText txt_email, txt_password;
    Button btn_login;

    MorentAPI morentAPI = new MorentAPI();
    ProgressDialog pDialog;

    ConnectivityManager conMgr;

    MaterialTextView have_acc;

    private String urlLogin = morentAPI.URL_LOGIN;
    private static final String TAG = LoginActivity.class.getSimpleName();

    private static final String TAG_MESSAGE = "message";

    public final static String TAG_ID = "id";
    public final static String TAG_FULL_NAME = "full_name";
    public final static String TAG_EMAIL = "email";
    public final static String TAG_ALAMAT = "alamat";

    SharedPreferences sharedpreferences;
    Boolean session = false, verify = false;
    String id, nama, email, alamat;
    public static final String loginPreferences = "loginPreferences";
    public static final String session_status = "session_status";
    public static final String verify_status = "verify_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_email = findViewById(R.id.input_email);
        txt_password = findViewById(R.id.input_password);
        btn_login = findViewById(R.id.btn_login);
        have_acc = findViewById(R.id.have_acc);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        // Cek session login jika TRUE maka langsung buka Dashbard
        sharedpreferences = getSharedPreferences(loginPreferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        verify = sharedpreferences.getBoolean(verify_status, false);
        id = sharedpreferences.getString(TAG_ID, null);
        nama = sharedpreferences.getString(TAG_FULL_NAME, null);
        email = sharedpreferences.getString(TAG_EMAIL, null);
        alamat = sharedpreferences.getString(TAG_ALAMAT, null);

        //EDIT INI CUYY NANTI, INI CUMA UNTUK COBA"
//        session = true;
//        verify = true;

        if (session && verify) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra(TAG_ID, id);
            intent.putExtra(TAG_FULL_NAME, nama);
            intent.putExtra(TAG_EMAIL, email);
            intent.putExtra(TAG_ALAMAT, alamat);
            finish();
            startActivity(intent);
        }
        else if (session) {
            Intent intent = new Intent(LoginActivity.this, VerifActivity.class);
            intent.putExtra(TAG_ID, id);
            intent.putExtra(TAG_FULL_NAME, nama);
            intent.putExtra(TAG_EMAIL, email);
            intent.putExtra(TAG_ALAMAT, alamat);
            finish();
            startActivity(intent);
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txt_email.getText().toString();
                String password = txt_password.getText().toString();

                // Mengecek inputan yang kosong
                if (email.trim().length() > 0 && password.trim().length() > 0) {
                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {
                        checkLogin(email, password);
                    } else {
                        Toast.makeText(getApplicationContext() ,"No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext() ,"Inputan tidak boleh kosong", Toast.LENGTH_LONG).show();
                }

            }
        });

        // When Have Acc Clicked
        have_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });
    }

    //Fungsi menampilkan data mahasiswa
    public void checkLogin(String email, String password) {
        //Pendeklarasian queue
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Login");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Memulai membuat permintaan request menghapus data ke jaringan
        StringRequest stringRequest = new StringRequest(POST, urlLogin, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                        progressDialog.dismiss();
                        try {
                            //Mengubah response string menjadi object
                            JSONObject jsonObject = new JSONObject(response);

                            JSONObject userJson = jsonObject.getJSONObject("data");

                            String id = userJson.getString(TAG_ID);
                            String nama = userJson.getString(TAG_FULL_NAME);
                            String email = userJson.getString(TAG_EMAIL);
                            String alamat = userJson.getString(TAG_ALAMAT);

                            if(jsonObject.getString("message").equals("Login Success"))
                            {
                                // menyimpan login ke session
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putBoolean(session_status, true);
                                editor.putBoolean(verify_status, true);
                                editor.putString(TAG_ID, id);
                                editor.putString(TAG_FULL_NAME, nama);
                                editor.putString(TAG_EMAIL, email);
                                editor.putString(TAG_ALAMAT, alamat);
                                editor.commit();

                                // Memanggil Home
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                intent.putExtra(TAG_ID, id);
                                intent.putExtra(TAG_FULL_NAME, nama);
                                intent.putExtra(TAG_EMAIL, email);
                                intent.putExtra(TAG_ALAMAT, alamat);
                                finish();
                                startActivity(intent);
                            }
//                            else {
//
//                                // menyimpan login ke session
//                                SharedPreferences.Editor editor = sharedpreferences.edit();
//                                editor.putBoolean(session_status, true);
//                                editor.putBoolean(verify_status, false);
//                                editor.putString(TAG_ID, id);
//                                editor.putString(TAG_FULL_NAME, nama);
//                                editor.putString(TAG_EMAIL, email);
//                                editor.commit();
//
//                                // Memanggil VerifActivity
//                                Intent intent = new Intent(LoginActivity.this, VerifActivity.class);
//                                intent.putExtra(TAG_ID, id);
//                                intent.putExtra(TAG_FULL_NAME, nama);
//                                intent.putExtra(TAG_EMAIL, email);
//                                finish();
//                                startActivity(intent);
//                            }

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

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

                        JSONObject userJson = jsonObject.getJSONObject("data");
                        String id = userJson.getString(TAG_ID);
                        String nama = userJson.getString(TAG_FULL_NAME);
                        String email = userJson.getString(TAG_EMAIL);
                        String alamat = userJson.getString(TAG_ALAMAT);

                        if(status.equals("verify")){

                            // menyimpan login ke session
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putBoolean(session_status, true);
                            editor.putBoolean(verify_status, false);
                            editor.putString(TAG_ID, id);
                            editor.putString(TAG_FULL_NAME, nama);
                            editor.putString(TAG_EMAIL, email);
                            editor.putString(TAG_ALAMAT, alamat);
                            editor.commit();

                            // Memanggil VerifActivity
                            Intent intent = new Intent(LoginActivity.this, VerifActivity.class);
                            intent.putExtra(TAG_ID, id);
                            intent.putExtra(TAG_FULL_NAME, nama);
                            intent.putExtra(TAG_EMAIL, email);
                            intent.putExtra(TAG_ALAMAT, alamat);
                            finish();
                            startActivity(intent);
                        }
                        else if(status.equals("unauth")){
                            Toast.makeText(getApplicationContext(), "Email / Password Salah", Toast.LENGTH_LONG).show();
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
                params.put("password", password);
                return params;
            }
        };
        // Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }
}
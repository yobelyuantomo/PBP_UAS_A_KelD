package com.kelompokd.pbp_uas_a_keld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kelompokd.pbp_uas_a_keld.API.MorentAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class RegisterActivity extends AppCompatActivity {

    Button btn_register;
    TextInputEditText email_input, pass_input, pass_input2, fullname_input;
    MaterialTextView already_member;
    ConnectivityManager conMgr;

    private static final String TAG_MESSAGE = "message";

    public final static String TAG_ID = "id";
    public final static String TAG_FULL_NAME = "full_name";
    public final static String TAG_EMAIL = "email";

    SharedPreferences sharedpreferences;
    Boolean session = false, verify = false;
    String id, nama, email;
    public static final String loginPreferences = "loginPreferences";
    public static final String session_status = "session_status";
    public static final String verify_status = "verify_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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

        if (session) {
            Intent intent = new Intent(RegisterActivity.this, VerifActivity.class);
            intent.putExtra(TAG_ID, id);
            intent.putExtra(TAG_FULL_NAME, nama);
            intent.putExtra(TAG_EMAIL, email);
            finish();
            startActivity(intent);
        }

        //Find The Layout ID in XML
        email_input = findViewById(R.id.input_email);
        fullname_input = findViewById(R.id.input_fullname);
        pass_input = findViewById(R.id.input_password);
        pass_input2 = findViewById(R.id.input_retype);
        btn_register = findViewById(R.id.btn_register);
        already_member = findViewById(R.id.already_member);

        //When SignUp Button Clicked
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = fullname_input.getText().toString();
                String email = email_input.getText().toString().trim();
                String password = pass_input.getText().toString().trim();
                String password2 = pass_input2.getText().toString().trim();

                if(TextUtils.isEmpty(fullname)){
                    Toast.makeText(getApplicationContext(), "Please Enter Full Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(email) || !email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                    Toast.makeText(getApplicationContext(), "Email Invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.length() < 6){
                    Toast.makeText(getApplicationContext(), "Password too Short", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!password.equals(password2)){
                    Toast.makeText(getApplicationContext(), "Password Confirm Doesn't Match", Toast.LENGTH_SHORT).show();
                    return;
                }

                register(fullname, email, password, password2);

            }
        });

        // When Already Member Clicked
        already_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });
    }

    public void register(final String full_name, final String email, final String password, final String password_confirmation){
        //Pendeklarasian queue
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Register");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Memulai membuat permintaan request menghapus data ke jaringan
        StringRequest stringRequest = new StringRequest(POST, MorentAPI.URL_REGISTER, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                        progressDialog.dismiss();
                        try {
                            //Mengubah response string menjadi object
                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getString("status").equals("success"))
                            {
                                JSONObject userJson = jsonObject.getJSONObject("data");

                                String id = userJson.getString(TAG_ID);
                                String nama = userJson.getString(TAG_FULL_NAME);
                                String email = userJson.getString(TAG_EMAIL);

                                Toast.makeText(getApplicationContext(), jsonObject.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                                // menyimpan login ke session
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putBoolean(session_status, true);
                                editor.putString(TAG_ID, id);
                                editor.putString(TAG_FULL_NAME, nama);
                                editor.putString(TAG_EMAIL, email);
                                editor.commit();

                                // Memanggil VerifyActifity
                                Intent intent = new Intent(RegisterActivity.this, VerifActivity.class);
                                intent.putExtra(TAG_ID, id);
                                intent.putExtra(TAG_FULL_NAME, nama);
                                intent.putExtra(TAG_EMAIL, email);
                                finish();
                                startActivity(intent);
                            }

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
                        JSONArray jsonArray = jsonObject.getJSONArray("message");
                        JSONArray jsonEmail = jsonArray.getJSONObject(0).getJSONArray("email");
                        String mEmailChild = jsonEmail.getString(0);

                        Toast.makeText(getApplicationContext(), mEmailChild, Toast.LENGTH_LONG).show();

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
                params.put("full_name", full_name);
                params.put("email", email);
                params.put("password", password);
                params.put("password_confirmation", password_confirmation);
                return params;
            }
        };

        DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        // Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }
}
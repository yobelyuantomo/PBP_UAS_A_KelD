package com.kelompokd.pbp_uas_a_keld.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.kelompokd.pbp_uas_a_keld.API.MorentAPI;
import com.kelompokd.pbp_uas_a_keld.HomeActivity;
import com.kelompokd.pbp_uas_a_keld.LoginActivity;
import com.kelompokd.pbp_uas_a_keld.R;
import com.kelompokd.pbp_uas_a_keld.VerifActivity;
import com.kelompokd.pbp_uas_a_keld.model.Mobil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class EditProfileFragment extends Fragment {

    MaterialButton btnBatal, btnSimpan;
    String full_name, email, tanggal_lahir, jenis_kelamin, alamat;
    TextInputEditText et_nama, et_tglLahir, et_email, et_alamat;
    int idUser;

    View root;

    String dateTimeLahir;
    long timeLahir;

    AutoCompleteTextView ed_jenisKelamin;
    private String sKelamin = "";
    private String[] saKelamin = new String[] {"Pria", "Wanita"};

    SimpleDateFormat dateFormatter = new SimpleDateFormat("E, dd MMM yyyy");
    SimpleDateFormat dateFormatter2 = new SimpleDateFormat("yyyy/MM/dd");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        idUser = getArguments().getInt("dataId");
        full_name = getArguments().getString("dataNama");
        email = getArguments().getString("dataEmail");
        tanggal_lahir = getArguments().getString("dataTglLahir");
        jenis_kelamin = getArguments().getString("dataJK");
        alamat = getArguments().getString("dataAlamat");

        init();

        btnBatal = root.findViewById(R.id.btnBatal);
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.nav_acc_settings);
            }
        });

        btnSimpan = root.findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUser(et_nama.getText().toString(), et_tglLahir.getText().toString(),
                        sKelamin, et_email.getText().toString(),
                        et_alamat.getText().toString(), "null");
            }
        });

        return root;
    }

    public void init(){
        et_nama = root.findViewById(R.id.et_nama);
        et_tglLahir = root.findViewById(R.id.et_tglLahir);
        ed_jenisKelamin = root.findViewById(R.id.ed_jenisKelamin);
        et_email = root.findViewById(R.id.et_email);
        et_alamat = root.findViewById(R.id.et_alamat);

        et_nama.setEnabled(false);
        et_nama.setText(full_name);
        et_tglLahir.setFocusable(false);
        et_tglLahir.setClickable(true);
        et_tglLahir.setText(tanggal_lahir);
        et_tglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimePicker();
            }
        });

        ed_jenisKelamin.setText(jenis_kelamin);

        ArrayAdapter<String> adapterKelamin = new ArrayAdapter<>(getContext(),
                R.layout.list_item, R.id.item_list, saKelamin);
        ed_jenisKelamin.setAdapter(adapterKelamin);
        ed_jenisKelamin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sKelamin = saKelamin[position];
            }
        });

        et_email.setEnabled(false);
        et_email.setText(email);
        et_alamat.setText(alamat);
    }

    public void editUser(String nama, String tgl_lahir,
                         String jenis_kelamin, String email,
                         String alamat, String profile_picture) {
        //Pendeklarasian queue
        RequestQueue queue = Volley.newRequestQueue(getContext());
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Ubah Data Akun");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Memulai membuat permintaan request menghapus data ke jaringan
        StringRequest stringRequest = new StringRequest(POST, MorentAPI.URL_UPDATE_USER + idUser, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                        progressDialog.dismiss();
                        try {
                            //Mengubah response string menjadi object
                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getString("message").equals("Update User Success"))
                            {
                                Navigation.findNavController(root).navigate(R.id.nav_acc_settings);
                            }

                            Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

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

                    Toast.makeText(getContext(), jsonError, Toast.LENGTH_LONG).show();
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
                params.put("full_name", nama);
                params.put("email", email);
                params.put("tanggal_lahir", tgl_lahir);
                params.put("jenis_kelamin", jenis_kelamin);
                params.put("jenis_kelamin", jenis_kelamin);
                params.put("alamat", alamat);
                params.put("profil_picture", profile_picture);
                return params;
            }
        };
        // Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }

    private void dateTimePicker(){
        final View dialogView = View.inflate(getActivity(), R.layout.date_time_picker, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();

        dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

                Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute());

                timeLahir = calendar.getTimeInMillis();
                dateTimeLahir = dateFormatter2.format(timeLahir);
                et_tglLahir.setText(dateTimeLahir);

                alertDialog.dismiss();
            }});
        alertDialog.setView(dialogView);
        alertDialog.show();
    }
}
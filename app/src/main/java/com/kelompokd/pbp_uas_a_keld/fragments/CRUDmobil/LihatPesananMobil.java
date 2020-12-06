package com.kelompokd.pbp_uas_a_keld.fragments.CRUDmobil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kelompokd.pbp_uas_a_keld.API.MorentAPI;
import com.kelompokd.pbp_uas_a_keld.R;
import com.kelompokd.pbp_uas_a_keld.adapter.AdapterSewaMobil;
import com.kelompokd.pbp_uas_a_keld.model.Pesanan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.GET;

public class LihatPesananMobil extends Fragment {

    View view;
    private RecyclerView recyclerView;
    private AdapterSewaMobil adapter;

    SharedPreferences sharedpreferences;
    public static final String loginPreferences = "loginPreferences";
    String id, nama, email, alamat;

    public final static String TAG_ID = "id";
    public final static String TAG_FULL_NAME = "full_name";
    public final static String TAG_EMAIL = "email";
    public final static String TAG_ALAMAT = "alamat";

    private List<Pesanan> listPesanan = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lihat_sewa_mobil, container, false);

        //Ambil data user dari data yang disimpan pada sharedpreferences
        sharedpreferences = getActivity().getSharedPreferences(loginPreferences, Context.MODE_PRIVATE);
        id = sharedpreferences.getString(TAG_ID, null);
        nama = sharedpreferences.getString(TAG_FULL_NAME, null);
        email = sharedpreferences.getString(TAG_EMAIL, null);
        alamat = sharedpreferences.getString(TAG_ALAMAT, null);

        setAdapter();
        getPesanan();

        return view;
    }

    public void setAdapter(){

        /*Buat tampilan untuk adapter jika potrait menampilkan 2 data dalam 1 baris,
        sedangakan untuk landscape 4 data dalam 1 baris*/

        recyclerView = view.findViewById(R.id.recycler_pesanan_mobil);
        adapter = new AdapterSewaMobil(view.getContext(), listPesanan, new AdapterSewaMobil.deleteItemListener() {
            @Override
            public void deleteItem(Boolean delete) {
                adapter.notifyDataSetChanged();
            }
        });

        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void getPesanan() {
        //Pendeklarasian queue
        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        //Meminta tanggapan string dari URL yang telah disediakan menggunakan method GET
        //untuk request ini tidak memerlukan parameter
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menampilkan data pesanan");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, MorentAPI.URL_SELECT_PESANAN_MOBIL + nama
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                progressDialog.dismiss();
                try {
                    //Mengambil data response json object yang berupa data mahasiswa
                    JSONArray jsonArray = response.getJSONArray("data");

                    if(!listPesanan.isEmpty())
                        listPesanan.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        //Mengubah data jsonArray tertentu menjadi json Object
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        int idPesanan           = jsonObject.optInt("id");
                        String namaPesanan      = jsonObject.optString("nama_pesanan");
                        String gambar           = jsonObject.optString("img_pesanan");
                        String durasi           = jsonObject.optString("durasi_pesan");
                        String tglPesan         = jsonObject.optString("tanggal_pesan");
                        String tglTransaksi     = jsonObject.optString("tanggal_transaksi");
                        String pemesan          = jsonObject.optString("nama");
                        String email            = jsonObject.optString("email");
                        String alamat           = jsonObject.optString("alamat");
                        String jenisPembayaran  = jsonObject.optString("jenis_pembayaran");
                        String noKartu          = jsonObject.optString("no_kartu");
                        String tagihan          = jsonObject.optString("selisih");

                        //Membuat objek user
                        Pesanan pesanan = new Pesanan(idPesanan, namaPesanan, pemesan, email, alamat,
                                jenisPembayaran, noKartu, tglPesan, Integer.parseInt(durasi), tglTransaksi,
                                Integer.parseInt(tagihan), gambar);

                        //Menambahkan objek user tadi ke list user
                        listPesanan.add(pesanan);
                    }
                    adapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                }
                Toast.makeText(view.getContext(), response.optString("message"),
                        Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Disini bagian jika response jaringan terdapat ganguan/error
                progressDialog.dismiss();
                NetworkResponse networkResponse = error.networkResponse;

                if (networkResponse != null && networkResponse.data != null) {
                    String jsonError = new String(networkResponse.data);
                    Toast.makeText(getContext(), jsonError, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }
}
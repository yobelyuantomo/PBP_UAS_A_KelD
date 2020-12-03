package com.kelompokd.pbp_uas_a_keld.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kelompokd.pbp_uas_a_keld.API.MorentAPI;
import com.kelompokd.pbp_uas_a_keld.R;
import com.kelompokd.pbp_uas_a_keld.adapter.AdapterWisata;
import com.kelompokd.pbp_uas_a_keld.model.Wisata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.GET;

public class WisataFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterWisata adapter;
    private List<Wisata> listWisata;
    private List<Wisata> dataWisata = new ArrayList<>();
    private View view;

    private List<Integer> checked = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wisata, container, false);

        // Ketika Float Action Button di Click
        FloatingActionButton faBtn_wishlist = view.findViewById(R.id.faBtn_wishlist);
        FloatingActionButton faBtn_pesan= view.findViewById(R.id.faBtn_pesan);

        faBtn_pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked = adapter.getJenisChecked();
                if (checked.isEmpty()){
                    Toast.makeText(v.getContext(), "Just check, please!", Toast.LENGTH_SHORT).show();
                }else {
                    for(int i=0; i<checked.size(); i++){
                        getWisataId(checked.get(i), i);
                    }
                }
            }
        });

        loadDaftarWisata();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void loadDaftarWisata(){
        setAdapter();
        getWisata();
    }

    public void setAdapter(){
        getActivity().setTitle("Data Wisata");

        /*Buat tampilan untuk adapter jika potrait menampilkan 2 data dalam 1 baris,
        sedangakan untuk landscape 4 data dalam 1 baris*/
        listWisata = new ArrayList<Wisata>();
        recyclerView = view.findViewById(R.id.recycler_wisata_list);
        adapter = new AdapterWisata(view.getContext(), listWisata);

        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void getWisata() {
        //Pendeklarasian queue
        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        //Meminta tanggapan string dari URL yang telah disediakan menggunakan method GET
        //untuk request ini tidak memerlukan parameter
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menampilkan paket wisata");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, MorentAPI.URL_SELECT_WISATA
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                progressDialog.dismiss();
                try {
                    //Mengambil data response json object yang berupa data mahasiswa
                    JSONArray jsonArray = response.getJSONArray("data");

                    if(!listWisata.isEmpty())
                        listWisata.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        //Mengubah data jsonArray tertentu menjadi json Object
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        int idWisata        = jsonObject.optInt("id");
                        String nama_wisata  = jsonObject.optString("nama_wisata");
                        int harga           = jsonObject.optInt("harga");
                        int durasi          = jsonObject.optInt("durasi");
                        String fasilitas    = jsonObject.optString("fasilitas");
                        int kuota           = jsonObject.optInt("kuota");
                        String gambar       = jsonObject.optString("img_wisata");

                        //Membuat objek Wisata
                        Wisata wisata = new Wisata(idWisata, nama_wisata, harga, durasi, fasilitas, kuota, gambar);

                        //Menambahkan objek user tadi ke list user
                        listWisata.add(wisata);
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
                Toast.makeText(view.getContext(), error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }

    public void getWisataId(int id, int loop){
        //Pendeklarasian queue
        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        //Meminta tanggapan string dari URL yang telah disediakan menggunakan method GET
        //untuk request ini tidak memerlukan parameter
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menampilkan paket wisata");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, MorentAPI.URL_SHOW_WISATA + id
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                progressDialog.dismiss();
                try {
                    //Mengambil data response json object yang berupa data mahasiswa
                    JSONObject jsonObject = response.getJSONObject("data");

                    int idWisata        = jsonObject.optInt("id");
                    String nama_wisata  = jsonObject.optString("nama_wisata");
                    int harga           = jsonObject.optInt("harga");
                    int durasi          = jsonObject.optInt("durasi");
                    String fasilitas    = jsonObject.optString("fasilitas");
                    int kuota           = jsonObject.optInt("kuota");
                    String gambar       = jsonObject.optString("img_wisata");

                    //Membuat objek Wisata
                    Wisata wisata = new Wisata(idWisata, nama_wisata, harga, durasi, fasilitas, kuota, gambar);

                    //Menambahkan objek user tadi ke list user
                    dataWisata.add(wisata);

                    if(loop == checked.size()-1){
                        //TODO 4.4(2,5)
                        Bundle data = new Bundle();
                        data.putSerializable("wisata", (Serializable) dataWisata);
                        Navigation.findNavController(view).navigate(R.id.nav_checkout_wisata, data);
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Disini bagian jika response jaringan terdapat ganguan/error
                progressDialog.dismiss();
                Toast.makeText(view.getContext(), error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }
}
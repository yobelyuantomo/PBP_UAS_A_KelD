package com.kelompokd.pbp_uas_a_keld.PesanPaketWisataTesting;

import android.app.ProgressDialog;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kelompokd.pbp_uas_a_keld.API.MorentAPI;
import com.kelompokd.pbp_uas_a_keld.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class PesanWisataService
{
    public void pesanWisata(final PesanWisataView view, String nama_pesanan, String img_pesanan, String durasi_pesan,
                            String tanggal_pesanan, String tanggal_transaksi, String nama, String email, String alamat,
                            String jenis_pembayaran, String no_kartu, int selisih, int indicator, final PesanWisataCallback callback)
    {
        RequestQueue queue = Volley.newRequestQueue(view.getPesanWisataContext());

        StringRequest stringRequest = new StringRequest(POST, MorentAPI.URL_ADD_PESANAN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Mengubah response string menjadi object
                    JSONObject obj = new JSONObject(response);

                    if(obj.getString("message").equals("Add Pesanan Success")) {
                        callback.onSuccess(true);
                    }
                    else{
                        callback.onError();
                        view.showPesanWisataError(obj.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                /*
                    Disini adalah proses memasukan/mengirimkan parameter key dengan data value,
                    dan nama key nya harus sesuai dengan parameter key yang diminta oleh jaringan
                    API.
                */
                Map<String, String>  params = new HashMap<String, String>();
                params.put("nama_pesanan", nama_pesanan);
                params.put("img_pesanan", img_pesanan);
                params.put("durasi_pesan", durasi_pesan);
                params.put("tanggal_pesan", tanggal_pesanan);
                params.put("tanggal_transaksi", tanggal_transaksi);
                params.put("nama", nama);
                params.put("email", email);
                params.put("alamat", alamat);
                params.put("jenis_pembayaran", jenis_pembayaran);
                params.put("no_kartu", no_kartu);
                params.put("selisih", String.valueOf(selisih));

                return params;
            }
        };

        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }
    public Boolean getValid(final PesanWisataView view, String nama_pesanan, String img_pesanan, String durasi_pesan,
                            String tanggal_pesanan, String tanggal_transaksi, String nama, String email, String alamat,
                            String jenis_pembayaran, String no_kartu, int selisih, int indicator)
    {
        final Boolean[] bool = new Boolean[1];

        pesanWisata(view, nama_pesanan, img_pesanan, durasi_pesan,
            tanggal_pesanan, tanggal_transaksi, nama, email, alamat,
            jenis_pembayaran, no_kartu, selisih, indicator, new PesanWisataCallback()

        {
            @Override
            public void onSuccess(boolean value)
            {
                bool[0] = true;
            }

            @Override
            public void onError()
            {
                bool[0] = false;
            }
        });
        return bool[0];
    }
}

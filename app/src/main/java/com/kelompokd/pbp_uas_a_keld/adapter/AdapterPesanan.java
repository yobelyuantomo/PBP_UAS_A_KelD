package com.kelompokd.pbp_uas_a_keld.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.card.MaterialCardView;
import com.kelompokd.pbp_uas_a_keld.API.MorentAPI;
import com.kelompokd.pbp_uas_a_keld.R;
import com.kelompokd.pbp_uas_a_keld.model.Pesanan;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.android.volley.Request.Method.DELETE;
import static com.android.volley.Request.Method.POST;

public class AdapterPesanan extends RecyclerView.Adapter<AdapterPesanan.adapterPesananViewHolder> {

    private List<Pesanan> pesananList;
    private List<Pesanan> pesananListFiltered;
    private Context context;
    private View view;
    private AdapterPesanan.deleteItemListener mListener;

    SimpleDateFormat dateFormatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm");

    public AdapterPesanan(Context context, List<Pesanan> pesananList,
                          AdapterPesanan.deleteItemListener mListener) {
        this.context            = context;
        this.pesananList           = pesananList;
        this.pesananListFiltered   = pesananList;
        this.mListener          = mListener;
    }

    public interface deleteItemListener {
        void deleteItem( Boolean delete);
    }

    @NonNull
    @Override
    public AdapterPesanan.adapterPesananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.adapter_pesanan, parent, false);
        return new AdapterPesanan.adapterPesananViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPesanan.adapterPesananViewHolder holder, int position) {
        final Pesanan pesanan = pesananListFiltered.get(position);

        NumberFormat formatter = new DecimalFormat("#,###");
        holder.txtPesanan.setText(pesanan.getPesanan());
        holder.txtPemesan.setText(pesanan.getPemesan());
        holder.txtTglPesan.setText(pesanan.getTglPesan());
        holder.txtDurasi.setText(Integer.toString(pesanan.getDurasi()));
        holder.txtTglTransaksi.setText(pesanan.getTglTransaksi());
        if(pesanan.getTagihan() != 0){
            holder.txtTagihan.setText("Rp "+ formatter.format(pesanan.getTagihan()));
        }
        else{
            holder.txtTagihan.setText("Lunas");
            holder.txtTagihan.setTextColor(Color.parseColor("#00FF00"));
        }
        Glide.with(context)
                .load(pesanan.getGambar())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.iv_pesanan);

        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogDelete(pesanan);
            }
        });

        holder.editItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }

    public void alertDialogDelete(Pesanan pesanan){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage("Apakah anda ingin membatalkan pesanan?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        pesananList.remove(pesanan);
                        pesananListFiltered.remove(pesanan);
                        mListener.deleteItem(true);
                        deletePesanan(pesanan.getIdPesanan());
                    }
                }).setNegativeButton("Tidak", null);

        AlertDialog alert1 = alert.create();
        alert1.show();
    }

    @Override
    public int getItemCount() {
        return (pesananListFiltered != null) ? pesananListFiltered.size() : 0;
    }

    public class adapterPesananViewHolder extends RecyclerView.ViewHolder {
        private TextView txtPesanan, txtPemesan, txtTglPesan, txtDurasi, txtTglTransaksi, txtTagihan;
        private ImageView iv_pesanan;
        private MaterialCardView itemCard;
        private ImageButton deleteItem, editItem;

        public adapterPesananViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPesanan         = itemView.findViewById(R.id.tvPesanan);
            txtPemesan         = itemView.findViewById(R.id.tvPemesan);
            txtTglPesan         = itemView.findViewById(R.id.tvTglPesan);
            txtDurasi         = itemView.findViewById(R.id.tvDurasi);
            txtTglTransaksi         = itemView.findViewById(R.id.tvTglTransaksi);
            txtTagihan         = itemView.findViewById(R.id.tvTagihan);
            iv_pesanan        = itemView.findViewById(R.id.iv_pesanan);

            itemCard = itemView.findViewById(R.id.item_card);
            deleteItem = itemView.findViewById(R.id.deleteItem);
            editItem = itemView.findViewById(R.id.editItem);
        }
    }

    public void deletePesanan(int idPesanan){

        //Tambahkan hapus buku disini
        //Pendeklarasian queue
        RequestQueue queue = Volley.newRequestQueue(context);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menghapus data pesanan");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Memulai membuat permintaan request menghapus data ke jaringan
        StringRequest stringRequest = new StringRequest(POST, MorentAPI.URL_DELETE_PESANAN + idPesanan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                progressDialog.dismiss();
                try {
                    //Mengubah response string menjadi object
                    JSONObject obj = new JSONObject(response);
                    //obj.getString("message") digunakan untuk mengambil pesan message dari response
                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    mListener.deleteItem(true);
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
                    Toast.makeText(context, jsonError, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }
}
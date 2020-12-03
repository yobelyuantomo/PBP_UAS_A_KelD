package com.kelompokd.pbp_uas_a_keld.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.google.android.material.card.MaterialCardView;
import com.kelompokd.pbp_uas_a_keld.Cars;
import com.kelompokd.pbp_uas_a_keld.R;
import com.kelompokd.pbp_uas_a_keld.model.Wisata;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterWisata extends RecyclerView.Adapter<AdapterWisata.adapterWisataViewHolder> {

    private List<Wisata> wisataList;
    private List<Wisata> wisataListFiltered;
    private Context context;
    private View view;

    //Membuat variabel penampung untuk title receipt yang "checked"
    public ArrayList<Integer> checked = new ArrayList<>();

    public AdapterWisata(Context context, List<Wisata> wisataList) {
        this.context            = context;
        this.wisataList           = wisataList;
        this.wisataListFiltered   = wisataList;
    }

    //Membuat fungsi yang mengembalikan list title receipt yang "checked"
    public ArrayList<Integer> getJenisChecked(){
        return checked;
    }

    @NonNull
    @Override
    public AdapterWisata.adapterWisataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.adapter_wisatalist, parent, false);
        return new AdapterWisata.adapterWisataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterWisata.adapterWisataViewHolder holder, int position) {
        final Wisata wisata = wisataListFiltered.get(position);

        NumberFormat formatter = new DecimalFormat("#,###");
        holder.txtWisata.setText(wisata.getWisata());
        holder.txtHarga.setText("Rp "+ formatter.format(wisata.getHarga()));
        holder.txtDurasi.setText(Integer.toString(wisata.getDurasi()));
        holder.txtFasilitas.setText(wisata.getFasilitas());
        holder.txtKuota.setText(Integer.toString(wisata.getKuota()));
        Glide.with(context)
                .load(wisata.getGambar())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.iv_wisata);

        holder.itemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemCard.setChecked(!holder.itemCard.isChecked());
                //TODO 3.4(2,5)
                //Fungsi untuk menambah ataupun menghapus dipanggil disini
                int index = holder.getAdapterPosition();
                addRemoveJenis(holder, index, v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (wisataListFiltered != null) ? wisataListFiltered.size() : 0;
    }

    public class adapterWisataViewHolder extends RecyclerView.ViewHolder {
        private TextView txtWisata, txtHarga, txtDurasi, txtFasilitas, txtKuota;
        private ImageView iv_wisata;
        private MaterialCardView itemCard;

        public adapterWisataViewHolder(@NonNull View itemView) {
            super(itemView);
            txtWisata         = itemView.findViewById(R.id.tvWisata);
            txtHarga         = itemView.findViewById(R.id.tvHarga);
            txtDurasi         = itemView.findViewById(R.id.tvDurasi);
            txtFasilitas         = itemView.findViewById(R.id.tvFasilitas);
            txtKuota         = itemView.findViewById(R.id.tvKuota);
            iv_wisata        = itemView.findViewById(R.id.iv_wisata);

            itemCard = itemView.findViewById(R.id.item_card);
        }
    }
    //Membuat fungsi untuk menambahkan title receipt yang checked dan menghapus yang unchecked
    public void addRemoveJenis(final adapterWisataViewHolder holder, final int i, View v){
        int idWisata;

        idWisata = wisataList.get(i).getIdWisata();


        if(holder.itemCard.isChecked()){
            //Menambahkan objek user tadi ke list user
            checked.add(idWisata);
        }
        else if(!holder.itemCard.isChecked()){
            checked.remove(checked.indexOf(idWisata));
        }
    }
}
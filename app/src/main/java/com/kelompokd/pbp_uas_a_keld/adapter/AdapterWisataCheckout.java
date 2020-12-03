package com.kelompokd.pbp_uas_a_keld.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.card.MaterialCardView;
import com.kelompokd.pbp_uas_a_keld.R;
import com.kelompokd.pbp_uas_a_keld.model.Wisata;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterWisataCheckout extends RecyclerView.Adapter<AdapterWisataCheckout.adapterWisataViewHolder> {

    private List<Wisata> wisataList;
    private List<Wisata> wisataListFiltered;
    private Context context;
    private View view;
    private AdapterWisataCheckout.deleteItemListener mListener;

    public AdapterWisataCheckout(Context context, List<Wisata> wisataList,
                                 AdapterWisataCheckout.deleteItemListener mListener) {
        this.context            = context;
        this.wisataList           = wisataList;
        this.wisataListFiltered   = wisataList;
        this.mListener          = mListener;
    }

    public interface deleteItemListener {
        void deleteItem( Boolean delete);
    }

    @NonNull
    @Override
    public AdapterWisataCheckout.adapterWisataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.adapter_wisata_checkout, parent, false);
        return new AdapterWisataCheckout.adapterWisataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterWisataCheckout.adapterWisataViewHolder holder, int position) {
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

        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(wisataList.size() != 1 || wisataListFiltered.size() != 1){
                   wisataList.remove(wisata);
                   wisataListFiltered.remove(wisata);
                   mListener.deleteItem(true);
               }
               else{
                   Toast.makeText(context, "Item yang dipesan tidak boleh kosong!", Toast.LENGTH_SHORT).show();
               }
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
        private ImageButton deleteItem;

        public adapterWisataViewHolder(@NonNull View itemView) {
            super(itemView);
            txtWisata         = itemView.findViewById(R.id.tvWisata);
            txtHarga         = itemView.findViewById(R.id.tvHarga);
            txtDurasi         = itemView.findViewById(R.id.tvDurasi);
            txtFasilitas         = itemView.findViewById(R.id.tvFasilitas);
            txtKuota         = itemView.findViewById(R.id.tvKuota);
            iv_wisata        = itemView.findViewById(R.id.iv_wisata);

            itemCard = itemView.findViewById(R.id.item_card);
            deleteItem = itemView.findViewById(R.id.deleteItem);
        }
    }
}
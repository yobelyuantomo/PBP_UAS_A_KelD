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
import com.kelompokd.pbp_uas_a_keld.model.Mobil;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class AdapterMobilCheckout extends RecyclerView.Adapter<AdapterMobilCheckout.adapterMobilViewHolder> {

    private List<Mobil> mobilList;
    private List<Mobil> mobilListFiltered;
    private Context context;
    private View view;
    private AdapterMobilCheckout.deleteItemListener mListener;

    public AdapterMobilCheckout(Context context, List<Mobil> mobilList,
                                AdapterMobilCheckout.deleteItemListener mListener) {
        this.context            = context;
        this.mobilList           = mobilList;
        this.mobilListFiltered   = mobilList;
        this.mListener          = mListener;
    }

    public interface deleteItemListener {
        void deleteItem( Boolean delete);
    }

    @NonNull
    @Override
    public AdapterMobilCheckout.adapterMobilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.adapter_mobil_checkout, parent, false);
        return new AdapterMobilCheckout.adapterMobilViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMobilCheckout.adapterMobilViewHolder holder, int position) {
        final Mobil mobil = mobilListFiltered.get(position);

        NumberFormat formatter = new DecimalFormat("#,###");
        holder.txtMobil.setText(mobil.getMobil());
        holder.txtHarga.setText("Rp "+ formatter.format(mobil.getHarga()));
        holder.txtPemakaian.setText(Integer.toString(mobil.getPemakaian()));
        holder.txtFasilitas.setText(mobil.getFasilitas());
        holder.txtPenumpang.setText(Integer.toString(mobil.getMax_penumpang()));
        Glide.with(context)
                .load(mobil.getGambar())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.iv_mobil);

        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(mobilList.size() != 1 || mobilListFiltered.size() != 1){
                   mobilList.remove(mobil);
                   mobilListFiltered.remove(mobil);
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
        return (mobilListFiltered != null) ? mobilListFiltered.size() : 0;
    }

    public class adapterMobilViewHolder extends RecyclerView.ViewHolder {
        private TextView txtMobil, txtHarga, txtPemakaian, txtFasilitas, txtPenumpang;
        private ImageView iv_mobil;
        private MaterialCardView itemCard;
        private ImageButton deleteItem;

        public adapterMobilViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMobil         = itemView.findViewById(R.id.tvMobil);
            txtHarga         = itemView.findViewById(R.id.tvHarga);
            txtPemakaian         = itemView.findViewById(R.id.tvPemakaian);
            txtFasilitas         = itemView.findViewById(R.id.tvFasilitas);
            txtPenumpang         = itemView.findViewById(R.id.tvPenumpang);
            iv_mobil        = itemView.findViewById(R.id.iv_mobil);

            itemCard = itemView.findViewById(R.id.item_card);
            deleteItem = itemView.findViewById(R.id.deleteItem);
        }
    }
}
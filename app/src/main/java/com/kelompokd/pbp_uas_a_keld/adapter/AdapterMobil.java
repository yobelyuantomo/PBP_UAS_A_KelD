package com.kelompokd.pbp_uas_a_keld.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.card.MaterialCardView;
import com.kelompokd.pbp_uas_a_keld.R;
import com.kelompokd.pbp_uas_a_keld.model.Mobil;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterMobil extends RecyclerView.Adapter<AdapterMobil.adapterMobilViewHolder> {

    private List<Mobil> mobilList;
    private List<Mobil> mobilListFiltered;
    private Context context;
    private View view;

    //Membuat variabel penampung untuk title receipt yang "checked"
    public ArrayList<Integer> checked = new ArrayList<>();

    public AdapterMobil(Context context, List<Mobil> mobilList) {
        this.context            = context;
        this.mobilList           = mobilList;
        this.mobilListFiltered   = mobilList;
    }

    //Membuat fungsi yang mengembalikan list title receipt yang "checked"
    public ArrayList<Integer> getJenisChecked(){
        return checked;
    }

    @NonNull
    @Override
    public AdapterMobil.adapterMobilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.adapter_mobillist, parent, false);
        return new AdapterMobil.adapterMobilViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMobil.adapterMobilViewHolder holder, int position) {
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
        return (mobilListFiltered != null) ? mobilListFiltered.size() : 0;
    }

    public class adapterMobilViewHolder extends RecyclerView.ViewHolder {
        private TextView txtMobil, txtHarga, txtPemakaian, txtFasilitas, txtPenumpang;
        private ImageView iv_mobil;
        private MaterialCardView itemCard;

        public adapterMobilViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMobil         = itemView.findViewById(R.id.tvMobil);
            txtHarga         = itemView.findViewById(R.id.tvHarga);
            txtPemakaian         = itemView.findViewById(R.id.tvPemakaian);
            txtFasilitas         = itemView.findViewById(R.id.tvFasilitas);
            txtPenumpang         = itemView.findViewById(R.id.tvPenumpang);
            iv_mobil        = itemView.findViewById(R.id.iv_mobil);

            itemCard = itemView.findViewById(R.id.item_card);
        }
    }
    //Membuat fungsi untuk menambahkan title receipt yang checked dan menghapus yang unchecked
    public void addRemoveJenis(final adapterMobilViewHolder holder, final int i, View v){
        int idMobil;

        idMobil = mobilList.get(i).getIdMobil();


        if(holder.itemCard.isChecked()){
            //Menambahkan objek user tadi ke list user
            checked.add(idMobil);
        }
        else if(!holder.itemCard.isChecked()){
            checked.remove(checked.indexOf(idMobil));
        }
    }
}
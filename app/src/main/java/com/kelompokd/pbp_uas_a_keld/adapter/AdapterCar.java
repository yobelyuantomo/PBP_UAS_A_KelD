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
import com.kelompokd.pbp_uas_a_keld.R;
import com.kelompokd.pbp_uas_a_keld.model.Car;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterCar extends RecyclerView.Adapter<AdapterCar.adapterCarViewHolder> {

    private List<Car> carList;
    private List<Car> carListFiltered;
    private Context context;
    private View view;

    //Membuat variabel penampung untuk title receipt yang "checked"
    public ArrayList<Integer> checked = new ArrayList<>();

    public AdapterCar(Context context, List<Car> carList) {
        this.context            = context;
        this.carList           = carList;
        this.carListFiltered   = carList;
    }

    //Membuat fungsi yang mengembalikan list title receipt yang "checked"
    public ArrayList<Integer> getJenisChecked(){
        return checked;
    }

    @NonNull
    @Override
    public AdapterCar.adapterCarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.adapter_wisatalist, parent, false);
        return new AdapterCar.adapterCarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCar.adapterCarViewHolder holder, int position) {
        final Car car = carListFiltered.get(position);

        NumberFormat formatter = new DecimalFormat("#,###");
        holder.txtCar.setText(car.getJenis());
        holder.txtHarga.setText("Rp "+ formatter.format(car.getHarga()));
        holder.txtPemakaian.setText(car.getLamaPemakaian());
        holder.txtFasilitas.setText(car.getFasilitas());
        holder.txtMax.setText(car.getMaxPenumpang());
//        TODO : not yet
//        Glide.with(context)
//                .load(car.getGambar())
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
//                .into(holder.iv_wisata);

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
        return (carListFiltered != null) ? carListFiltered.size() : 0;
    }

    public class adapterCarViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCar, txtHarga, txtPemakaian, txtFasilitas, txtMax;
        private ImageView iv_car;
        private MaterialCardView itemCard;

        public adapterCarViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCar         = itemView.findViewById(R.id.tvJenis);
            txtHarga         = itemView.findViewById(R.id.tvHarga);
            txtPemakaian         = itemView.findViewById(R.id.tvPemakaian);
            txtFasilitas         = itemView.findViewById(R.id.tvFasilitas);
            txtMax         = itemView.findViewById(R.id.tvMax);
            iv_car        = itemView.findViewById(R.id.iv_car);

            itemCard = itemView.findViewById(R.id.item_card);
        }
    }
    //Membuat fungsi untuk menambahkan title receipt yang checked dan menghapus yang unchecked
    public void addRemoveJenis(final adapterCarViewHolder holder, final int i, View v){
        int idCar;

        idCar = carList.get(i).getId();


        if(holder.itemCard.isChecked()){
            //Menambahkan objek user tadi ke list user
            checked.add(idCar);
        }
        else if(!holder.itemCard.isChecked()){
            checked.remove(checked.indexOf(idCar));
        }
    }
}
package com.kelompokd.pbp_uas_a_keld.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.kelompokd.pbp_uas_a_keld.Cars;
import com.kelompokd.pbp_uas_a_keld.R;
import com.kelompokd.pbp_uas_a_keld.databinding.AdapterCarlistBinding;

import java.util.ArrayList;
import java.util.List;

public class CarRecyclerViewAdapter extends RecyclerView.Adapter<CarRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<Cars> result;

    AdapterCarlistBinding binding;

    //Membuat variabel penampung untuk title receipt yang "checked"
    public ArrayList<String> checked = new ArrayList<String>();

    public CarRecyclerViewAdapter(Context context, List<Cars> result){
        this.context = context;
        this.result = result;
    }

    //Membuat fungsi yang mengembalikan list title receipt yang "checked"
    public ArrayList<String> getJenisChecked(){
        return checked;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_carlist, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Cars cars = result.get(position);
        binding.setCars(cars);
        binding.setImageUrl(cars.imageUrl);

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
        return result.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private AdapterCarlistBinding binding;

        private ImageView imageCar;
        private TextView jenis, harga, pemakaian, fasilitas, maxPenumpang;
        private MaterialCardView itemCard;

        public MyViewHolder(@NonNull AdapterCarlistBinding binding){

            super(binding.getRoot());
            this.binding = binding;

            imageCar = itemView.findViewById(R.id.iv_car);
            jenis = itemView.findViewById(R.id.tvJenis);
            harga = itemView.findViewById(R.id.tvHarga);
            pemakaian = itemView.findViewById(R.id.tvPemakaian);
            fasilitas = itemView.findViewById(R.id.tvFasilitas);
            maxPenumpang = itemView.findViewById(R.id.tvMax);

            itemCard = itemView.findViewById(R.id.item_card);
        }
    }
    //Membuat fungsi untuk menambahkan title receipt yang checked dan menghapus yang unchecked
    public void addRemoveJenis(final MyViewHolder holder, final int i, View v){
        if(holder.itemCard.isChecked()){
            checked.add(result.get(i).getJenis());
        }
        else if(!holder.itemCard.isChecked()){
            checked.remove(result.get(i).getJenis());
        }
    }
}
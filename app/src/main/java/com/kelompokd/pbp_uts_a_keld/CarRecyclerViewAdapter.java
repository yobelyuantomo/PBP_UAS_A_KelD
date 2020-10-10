package com.kelompokd.pbp_uts_a_keld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kelompokd.pbp_uts_a_keld.databinding.AdapterRecyclerViewBinding;

import java.util.List;

public class CarRecyclerViewAdapter extends RecyclerView.Adapter<CarRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<Car> result;

    AdapterRecyclerViewBinding binding;

    public CarRecyclerViewAdapter(Context context, List<Car> result){
        this.context = context;
        this.result = result;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_recycler_view, parent, false);
       return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Car car = result.get(position);
        binding.setCar(car);
        binding.setImageUrl(car.imageUrl);
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private AdapterRecyclerViewBinding binding;
        public MyViewHolder(@NonNull AdapterRecyclerViewBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onClick(View view) {
            Toast.makeText(context, "You touch me?", Toast.LENGTH_SHORT).show();
        }
    }
}
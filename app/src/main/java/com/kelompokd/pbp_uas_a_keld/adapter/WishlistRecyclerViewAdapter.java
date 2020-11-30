package com.kelompokd.pbp_uas_a_keld.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.kelompokd.pbp_uas_a_keld.R;
import com.kelompokd.pbp_uas_a_keld.database.DatabaseClient;
import com.kelompokd.pbp_uas_a_keld.model.Car;

import java.util.List;

public class WishlistRecyclerViewAdapter extends RecyclerView.Adapter<WishlistRecyclerViewAdapter.WishlistViewHolder> {
    private Context context;
    private Activity activity;
    private List<Car> carList;

    public WishlistRecyclerViewAdapter(Context context, List<Car> carList) {
        this.context = context;
        this.carList = carList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wishlist, parent, false);
        return new WishlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.textView.setText(car.getJenis());
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class WishlistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        MaterialTextView textView;
        MaterialCardView cardView;

        public WishlistViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.jenis_textview);
            cardView = itemView.findViewById(R.id.item_card_wishlist);

            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Car car = carList.get(getAdapterPosition());
            deletePopUp(car, v);
        }
    }

    // Ini fungsi untuk menampilkan pop up sebelum logout
    private void deletePopUp(final Car car, View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage("Are you sure want to delete?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialog, int which) {

                        delete(car, v);

                    }
                }).setNegativeButton("Cancel", null);

        AlertDialog alert1 = alert.create();
        alert1.show();
    }

    private void delete(Car car, View v){
        class Deletecar extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseClient.getInstance(context).getDatabase()
                        .carDao()
                        .delete(car);
                return null;
            }
        }

        Navigation.findNavController(v).navigate(R.id.nav_wishlist);

        Deletecar delete = new Deletecar();
        delete.execute();
    }
}

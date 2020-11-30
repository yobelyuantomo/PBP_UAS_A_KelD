package com.kelompokd.pbp_uas_a_keld.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kelompokd.pbp_uas_a_keld.Cars;
import com.kelompokd.pbp_uas_a_keld.CarList;
import com.kelompokd.pbp_uas_a_keld.adapter.CarRecyclerViewAdapter;
import com.kelompokd.pbp_uas_a_keld.R;
import com.kelompokd.pbp_uas_a_keld.database.DatabaseClient;
import com.kelompokd.pbp_uas_a_keld.model.Car;

import java.util.ArrayList;
import java.util.List;

public class SewaFragment extends Fragment {
    private ArrayList<Cars> listCars;
    private RecyclerView recyclerView;
    private CarRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<String> checked = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_sewa, container, false);

        ImageButton back_btn = root.findViewById(R.id.back_btn);
        ImageButton acc_setting_corner = root.findViewById(R.id.account_settings_corner);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.nav_home);
            }
        });

        acc_setting_corner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.nav_acc_settings);
            }
        });

        //get data car
        listCars = new CarList().Cars;

        //recycler view
        recyclerView = root.findViewById(R.id.recycler_car_list);
        adapter = new CarRecyclerViewAdapter(getContext(), listCars);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        // Ketika Float Action Button di Click
        FloatingActionButton faBtn_wishlist = root.findViewById(R.id.faBtn_wishlist);
        FloatingActionButton faBtn_pesan= root.findViewById(R.id.faBtn_pesan);

        faBtn_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 4.1(2,5)
                //Melakukan inisialisasi "checked" yang didapat dari fungsi yang berada pada recyclerViewAdapter
                //checked = recyclerViewAdapter. ....
                checked = adapter.getJenisChecked();

                if (checked.isEmpty()){
                    Toast.makeText(v.getContext(), "Just check, please!", Toast.LENGTH_SHORT).show();
                }else {
                    //TODO 4.4(2,5)
                    //Fungsi menampilkan dialog dipanggil disini
                    showDialog(root);
                }
            }
        });

        faBtn_pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*  Setelah memilih mobil mana saja yang akan di sewa (Bisa lebih dari satu) Langsung masuk ke fragment checkout, nanti pada fragment
                    checkout menampilkan mobil yang di pesan menggunakan adapter
                */

                Navigation.findNavController(root).navigate(R.id.nav_checkout);
            }
        });

        return root;
    }

    //Menampilkan simple dialog yang menampilkan jenis mobil yang checked
    public  void showDialog(View root){
        CharSequence[] carList = getStringArray(checked);
        String jenisDialog = "";
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(getContext());

        for(int i=0; i<checked.size(); i++){
            jenisDialog = jenisDialog + "\n" + carList[i] + "\n";
        }

        builder.setMessage(jenisDialog)
                .setPositiveButton("Simpan", new DialogInterface.OnClickListener()                 {

                    public void onClick(DialogInterface dialog, int which) {

                        simpanWishlist(carList, checked.size()); // Manggil fungsi simpan wishlist
                        Navigation.findNavController(root).navigate(R.id.nav_wishlist);

                    }
                }).setNegativeButton("Cancel", null);

        builder.setTitle("Simpan di Wishlist\n");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //Fungsi ini untuk menyimpan yang di cek ke dalam wishlist
    public void simpanWishlist(CharSequence[] carList, int j){
        for(int i=0; i<j; i++){
            getOneStored(carList[i].toString());
        }
    }

    // Menyimpan satu yang ada di alert dialog ke rooms
    private void getOneStored(String jenis){

        class AddJenis extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                Car car = new Car();
                car.setJenis(jenis);

                DatabaseClient.getInstance(getContext()).getDatabase()
                        .carDao()
                        .insert(car);
                return null;
            }
        }

        AddJenis add = new AddJenis();
        add.execute();
    }

    //Fungsi di bawah ini berfungsi untuk mengubah array list
    //kedalam bentuk array biasa.
    public static String[] getStringArray(List<String> input) {
        String[] strings = new String[input.size()];

        for (int j = 0; j < input.size(); j++) {
            strings[j] = input.get(j);
        }

        return strings;
    }
}
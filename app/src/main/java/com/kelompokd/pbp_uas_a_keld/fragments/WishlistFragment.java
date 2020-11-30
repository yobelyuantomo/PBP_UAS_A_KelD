package com.kelompokd.pbp_uas_a_keld.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.kelompokd.pbp_uas_a_keld.R;
import com.kelompokd.pbp_uas_a_keld.adapter.WishlistRecyclerViewAdapter;
import com.kelompokd.pbp_uas_a_keld.database.DatabaseClient;
import com.kelompokd.pbp_uas_a_keld.model.Car;

import java.util.List;

public class WishlistFragment extends Fragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wishlist, container, false);

        refreshLayout = root.findViewById(R.id.swipe_refresh);
        recyclerView = root.findViewById(R.id.recycler_wishlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getWishlist();
                refreshLayout.setRefreshing(false);
            }
        });

        getWishlist();

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

        return root;
    }

    private void getWishlist(){
        class GetWishlist extends AsyncTask<Void, Void, List<Car>> {

            @Override
            protected List<Car> doInBackground(Void... voids) {
                List<Car> carList = DatabaseClient
                        .getInstance(getContext())
                        .getDatabase()
                        .carDao()
                        .getAll();
                return carList;
            }

            @Override
            protected void onPostExecute(List<Car> cars) {
                super.onPostExecute(cars);
                WishlistRecyclerViewAdapter adapter = new WishlistRecyclerViewAdapter(getContext(), cars);
                recyclerView.setAdapter(adapter);
                if (cars.isEmpty()){
                    Toast.makeText(getContext(), "Empty List", Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetWishlist get = new GetWishlist();
        get.execute();
    }
}
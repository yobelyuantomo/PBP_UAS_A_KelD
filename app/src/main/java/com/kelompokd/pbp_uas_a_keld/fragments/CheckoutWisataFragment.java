package com.kelompokd.pbp_uas_a_keld.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kelompokd.pbp_uas_a_keld.API.MorentAPI;
import com.kelompokd.pbp_uas_a_keld.PesanPaketWisataTesting.ChekoutWisataActivityUtil;
import com.kelompokd.pbp_uas_a_keld.PesanPaketWisataTesting.PesanWisataPresenter;
import com.kelompokd.pbp_uas_a_keld.PesanPaketWisataTesting.PesanWisataService;
import com.kelompokd.pbp_uas_a_keld.PesanPaketWisataTesting.PesanWisataView;
import com.kelompokd.pbp_uas_a_keld.R;
import com.kelompokd.pbp_uas_a_keld.UnitTestLogin.LoginActivityUtil;
import com.kelompokd.pbp_uas_a_keld.UnitTestLogin.LoginPresenter;
import com.kelompokd.pbp_uas_a_keld.UnitTestLogin.LoginService;
import com.kelompokd.pbp_uas_a_keld.adapter.AdapterWisataCheckout;
import com.kelompokd.pbp_uas_a_keld.model.Wisata;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;

public class CheckoutWisataFragment extends Fragment implements PesanWisataView {

    TextInputEditText namaPemesan, tglPesan, tglTransaksi, totalBayar, noKartu, et_alamat;
    TextInputLayout layout_noKartu;
    MaterialButton btnBatal, btnCheckout;
    View view;
    AutoCompleteTextView exposedDropdownPayment;

    private int tagihan;

    private String sPayment = "";
    private String[] saPayment = new String[] {"Tunai", "Kartu Kredit"};
    NumberFormat numberFormatter = new DecimalFormat("#,###");

    private RecyclerView recyclerView;
    private AdapterWisataCheckout adapter;

    SharedPreferences sharedpreferences;
    public static final String loginPreferences = "loginPreferences";
    String id, nama, email, alamat;

    public final static String TAG_ID = "id";
    public final static String TAG_FULL_NAME = "full_name";
    public final static String TAG_EMAIL = "email";
    public final static String TAG_ALAMAT = "alamat";

    String dateTimePesan, dateTimeTransaksi;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm");
    SimpleDateFormat dateFormatter2 = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    private List<Wisata> wisata = new ArrayList<>();

    long timeTransaksi, timePesan;

    private PesanWisataPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        // The callback can be enabled or disabled here or in handleOnBackPressed()
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_checkout_wisata, container, false);

        presenter = new PesanWisataPresenter(this, new PesanWisataService());

        //Ambil data user dari data yang disimpan pada sharedpreferences
        sharedpreferences = getActivity().getSharedPreferences(loginPreferences, Context.MODE_PRIVATE);
        id = sharedpreferences.getString(TAG_ID, null);
        nama = sharedpreferences.getString(TAG_FULL_NAME, null);
        email = sharedpreferences.getString(TAG_EMAIL, null);
        alamat = sharedpreferences.getString(TAG_ALAMAT, null);

        wisata = (List<Wisata>) getArguments().getSerializable("wisata");

        init();

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog();
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dateTimePesan == null || sPayment == null || et_alamat.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Pastikan semua data terisi!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(sPayment.equals("Tunai")){
                        alertCheckoutDialog();
                    }
                    else{
                        if(String.valueOf(noKartu.getText()).isEmpty()){
                            Toast.makeText(getContext(), "Pastikan semua data terisi!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            alertCheckoutDialog();
                        }
                    }
                }
            }
        });

        return view;
    }

    public void init(){
        namaPemesan = view.findViewById(R.id.pemesan);
        tglPesan = view.findViewById(R.id.tglPesan);
        tglTransaksi = view.findViewById(R.id.tglTransaksi);
        et_alamat = view.findViewById(R.id.alamat);
        totalBayar = view.findViewById(R.id.totalBayar);
        exposedDropdownPayment = view.findViewById(R.id.paymentMethod);
        noKartu = view.findViewById(R.id.noKartu);
        btnBatal = view.findViewById(R.id.btnBatal);
        btnCheckout = view.findViewById(R.id.btnCheckout);
        layout_noKartu = view.findViewById(R.id.layout_noKartu);

        namaPemesan.setEnabled(false);
        namaPemesan.setText(nama);

        tglPesan.setFocusable(false);
        tglPesan.setClickable(true);
        tglPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimePicker();
            }
        });

        tglTransaksi.setEnabled(false);
        timeTransaksi = Calendar.getInstance().getTimeInMillis();
        dateTimeTransaksi = dateFormatter.format(timeTransaksi);
        tglTransaksi.setText(dateTimeTransaksi);

        setAdapter();

        et_alamat.setText(alamat);

        layout_noKartu.setVisibility(View.GONE);

        totalBayar.setEnabled(false);
        totalBayar.setText("Rp "+ numberFormatter.format(hitungTotalBayar()));

        ArrayAdapter<String> adapterPayment = new ArrayAdapter<>(getContext(),
                R.layout.list_item, R.id.item_list, saPayment);
        exposedDropdownPayment.setAdapter(adapterPayment);
        exposedDropdownPayment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sPayment = saPayment[position];
                if(sPayment.equals("Kartu Kredit")){
                    layout_noKartu.setVisibility(View.VISIBLE);
                }
                else{
                    layout_noKartu.setVisibility(View.GONE);
                }
            }
        });
    }

    public int hitungTotalBayar(){
        int jumlah = 0;

        for(int i=0; i < wisata.size(); i++){
            jumlah = jumlah + wisata.get(i).getHarga();
        }
        return jumlah;
    }

    public void setAdapter(){

        /*Buat tampilan untuk adapter jika potrait menampilkan 2 data dalam 1 baris,
        sedangakan untuk landscape 4 data dalam 1 baris*/

        recyclerView = view.findViewById(R.id.recycler_wisata_checkout);
        adapter = new AdapterWisataCheckout(view.getContext(), wisata, new AdapterWisataCheckout.deleteItemListener() {
            @Override
            public void deleteItem(Boolean delete) {
                adapter.notifyDataSetChanged();
                totalBayar.setText("Rp "+ numberFormatter.format(hitungTotalBayar()));
            }
        });

        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void alertDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setMessage("Apakah anda ingin membatalkan transaksi?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        Navigation.findNavController(view).navigate(R.id.nav_paketWisata);
                    }
                }).setNegativeButton("Tidak", null);

        AlertDialog alert1 = alert.create();
        alert1.show();
    }

    private int getSelisih(int i){
        int selisih = 0;

        if(sPayment.equals("Tunai")){
            selisih = wisata.get(i).getHarga();
        }
        else{
            selisih = 0;
        }

        return selisih;
    }

    private void alertCheckoutDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setMessage("Apakah anda yakin ingin membuat pesanan?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        String inputNoKartu = "";

                        if(sPayment.equals("Tunai")){
                            inputNoKartu = "";
                        }
                        else{
                            inputNoKartu = String.valueOf(noKartu.getText());
                        }

                        //////////////////////////////////////////////////////

                        presenter.onCheckoutClicked();

                        //////////////////////////////////////////////////////

                        for(int i=0; i < wisata.size(); i++){
                            addTransaction(wisata.get(i).getWisata(), wisata.get(i).getGambar(), Integer.toString(wisata.get(i).getDurasi()),
                                    dateFormatter2.format(timePesan), dateFormatter2.format(timeTransaksi), nama, email, alamat,
                                    sPayment, inputNoKartu, getSelisih(i), i);
                        }
                    }
                }).setNegativeButton("Tidak", null);

        AlertDialog alert1 = alert.create();
        alert1.show();
    }

    private void dateTimePicker(){
        final View dialogView = View.inflate(getActivity(), R.layout.date_time_picker, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();

        dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

                Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute());

                timePesan = calendar.getTimeInMillis();
                dateTimePesan = dateFormatter.format(timePesan);
                tglPesan.setText(dateTimePesan);

                alertDialog.dismiss();
            }});
        alertDialog.setView(dialogView);
        alertDialog.show();
    }

    public void addTransaction(String nama_pesanan, String img_pesanan, String durasi_pesan,
                               String tanggal_pesanan, String tanggal_transaksi, String nama, String email, String alamat,
                               String jenis_pembayaran, String no_kartu, int selisih, int indicator){

        //Tambahkan tambah buku disini
        //Pendeklarasian queue
        RequestQueue queue = Volley.newRequestQueue(getContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menambahkan data transaksi");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Memulai membuat permintaan request menghapus data ke jaringan
        StringRequest stringRequest = new StringRequest(POST, MorentAPI.URL_ADD_PESANAN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                progressDialog.dismiss();
                try {
                    //Mengubah response string menjadi object
                    JSONObject obj = new JSONObject(response);
                    //obj.getString("message") digunakan untuk mengambil pesan status dari response
                    if(indicator == wisata.size() - 1){
                        if(obj.getString("message").equals("Add Pesanan Success"))
                        {
                            Navigation.findNavController(view).navigate(R.id.action_nav_checkout_wisata_to_nav_lihatPesanan);
                        }
                        //obj.getString("message") digunakan untuk mengambil pesan message dari response
                        Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Disini bagian jika response jaringan terdapat ganguan/error
                progressDialog.dismiss();
                NetworkResponse networkResponse = error.networkResponse;

                if (networkResponse != null && networkResponse.data != null) {
                    String jsonError = new String(networkResponse.data);
                    Toast.makeText(getContext(), jsonError, Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                /*
                    Disini adalah proses memasukan/mengirimkan parameter key dengan data value,
                    dan nama key nya harus sesuai dengan parameter key yang diminta oleh jaringan
                    API.
                */
                Map<String, String>  params = new HashMap<String, String>();
                params.put("nama_pesanan", nama_pesanan);
                params.put("img_pesanan", img_pesanan);
                params.put("durasi_pesan", durasi_pesan);
                params.put("tanggal_pesan", tanggal_pesanan);
                params.put("tanggal_transaksi", tanggal_transaksi);
                params.put("nama", nama);
                params.put("email", email);
                params.put("alamat", alamat);
                params.put("jenis_pembayaran", jenis_pembayaran);
                params.put("no_kartu", no_kartu);
                params.put("selisih", String.valueOf(selisih));

                return params;
            }
        };

        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public String getNamaPesanan() {
        return wisata.get(0).getWisata();
    }

    @Override
    public void showNamaPesananError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getImgPesanan() {
        return wisata.get(0).getGambar();
    }

    @Override
    public void showImgPesananError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getDurasiPesan() {
        return Integer.toString(wisata.get(0).getDurasi());
    }

    @Override
    public void showDurasiPesanError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getTglPesan() {
        return dateFormatter2.format(timePesan);
    }

    @Override
    public void showTglPesanError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getTglTransaksi() {
        return dateFormatter2.format(timeTransaksi);
    }

    @Override
    public void showTglTransaksiError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getNama() {
        return namaPemesan.getText().toString();
    }

    @Override
    public void showNamaError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void showEmailError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getAlamat() {
        return et_alamat.getText().toString();
    }

    @Override
    public void showAlamatError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getJenisPembayaran() {
        return sPayment;
    }

    @Override
    public void showJenisPembayaranError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getNoKartu() {
        return noKartu.getText().toString();
    }

    @Override
    public void showNoKartuError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getSelisih() {
        return getSelisih(0);
    }

    @Override
    public Context getPesanWisataContext() {
        return getContext();
    }

    @Override
    public void startPesanWisataFragment() {
        new ChekoutWisataActivityUtil(getContext()).startCheckoutWisataFragment();
    }

    @Override
    public void showPesanWisataError(String message) {

    }
}
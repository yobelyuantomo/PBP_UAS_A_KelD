package com.kelompokd.pbp_uas_a_keld.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.Serializable;

@Entity
public class Car implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "jenis")
    public String jenis;
    public String harga;
    public String lamaPemakaian;
    public String fasilitas;
    public String maxPenumpang;
    public String imageUrl;

    public Car(String jenis, String harga, String lamaPemakaian, String fasilitas, String maxPenumpang, String imageUrl) {
        this.jenis = jenis;
        this.harga = harga;
        this.lamaPemakaian = lamaPemakaian;
        this.fasilitas = fasilitas;
        this.maxPenumpang = maxPenumpang;
        this.imageUrl = imageUrl;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getLamaPemakaian() {
        return lamaPemakaian;
    }

    public void setLamaPemakaian(String lamaPemakaian) {
        this.lamaPemakaian = lamaPemakaian;
    }

    public String getFasilitas() {
        return fasilitas;
    }

    public void setFasilitas(String fasilitas) {
        this.fasilitas = fasilitas;
    }

    public String getMaxPenumpang() {
        return maxPenumpang;
    }

    public void setMaxPenumpang(String maxPenumpang) {
        this.maxPenumpang = maxPenumpang;
    }

    @BindingAdapter("profileImage")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl).apply(new RequestOptions())
                .into(view);
    }
}



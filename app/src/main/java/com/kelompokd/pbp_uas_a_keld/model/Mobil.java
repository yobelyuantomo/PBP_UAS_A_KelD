package com.kelompokd.pbp_uas_a_keld.model;

import java.io.Serializable;

public class Mobil implements Serializable{
    private int idMobil;
    private String mobil, gambar, fasilitas;
    private int harga, max_penumpang, pemakaian;

    public Mobil(int idMobil, String mobil, int harga, int pemakaian, String fasilitas, int max_penumpang, String gambar) {
        this.idMobil = idMobil;
        this.mobil = mobil;
        this.harga = harga;
        this.pemakaian = pemakaian;
        this.fasilitas = fasilitas;
        this.max_penumpang = max_penumpang;
        this.gambar = gambar;
    }

    public int getIdMobil() {
        return idMobil;
    }

    public String getMobil() {
        return mobil;
    }

    public String getGambar() {
        return gambar;
    }

    public String getFasilitas() {
        return fasilitas;
    }

    public int getHarga() {
        return harga;
    }

    public int getMax_penumpang() {
        return max_penumpang;
    }

    public int getPemakaian() {
        return pemakaian;
    }
}

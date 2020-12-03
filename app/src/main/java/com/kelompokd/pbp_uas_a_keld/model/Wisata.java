package com.kelompokd.pbp_uas_a_keld.model;

import java.io.Serializable;

public class Wisata implements Serializable{
    private int idWisata;
    private String wisata, gambar, fasilitas;
    private int harga, kuota, durasi;

    public Wisata(int idWisata, String wisata, int harga, int durasi, String fasilitas, int kuota, String gambar) {
        this.idWisata = idWisata;
        this.wisata = wisata;
        this.harga = harga;
        this.durasi = durasi;
        this.fasilitas = fasilitas;
        this.kuota = kuota;
        this.gambar = gambar;
    }

    public int getIdWisata() {
        return idWisata;
    }

    public String getWisata() {
        return wisata;
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

    public int getKuota() {
        return kuota;
    }

    public int getDurasi() {
        return durasi;
    }
}

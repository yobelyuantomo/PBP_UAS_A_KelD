package com.kelompokd.pbp_uas_a_keld.model;

import java.io.Serializable;

public class Pesanan implements Serializable{
    private int idPesanan;
    private String pesanan, gambar, pemesan, tglPesan, tglTransaksi, email, alamat, jenisPembayaran, noKartu;
    private int durasi, tagihan;

    public Pesanan(int idPesanan, String pesanan, String pemesan, String email,
                   String alamat, String jenisPembayaran, String noKartu, String tglPesan,
                   int durasi, String tglTransaksi, int tagihan, String gambar) {
        this.idPesanan = idPesanan;
        this.pesanan = pesanan;
        this.pemesan = pemesan;
        this.tglPesan = tglPesan;
        this.durasi = durasi;
        this.email = email;
        this.alamat = alamat;
        this.jenisPembayaran = jenisPembayaran;
        this.noKartu = noKartu;
        this.tglTransaksi = tglTransaksi;
        this.tagihan = tagihan;
        this.gambar = gambar;
    }

    public int getIdPesanan() {
        return idPesanan;
    }

    public String getPesanan() {
        return pesanan;
    }

    public String getGambar() {
        return gambar;
    }

    public String getPemesan() {
        return pemesan;
    }

    public String getTglPesan() {
        return tglPesan;
    }

    public String getTglTransaksi() {
        return tglTransaksi;
    }

    public String getEmail() {
        return email;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getJenisPembayaran() {
        return jenisPembayaran;
    }

    public String getNoKartu() {
        return noKartu;
    }

    public int getDurasi() {
        return durasi;
    }

    public int getTagihan() {
        return tagihan;
    }
}

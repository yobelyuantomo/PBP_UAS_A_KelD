package com.kelompokd.pbp_uas_a_keld.PesanPaketWisataTesting;

import android.content.Context;

public interface PesanWisataView {
    String getNamaPesanan();
    void showNamaPesananError(String message);
    String getImgPesanan();
    void showImgPesananError(String message);
    String getDurasiPesan();
    void showDurasiPesanError(String message);
    String getTglPesan();
    void showTglPesanError(String message);
    String getTglTransaksi();
    void showTglTransaksiError(String message);
    String getNama();
    void showNamaError(String message);
    String getEmail();
    void showEmailError(String message);
    String getAlamat();
    void showAlamatError(String message);
    String getJenisPembayaran();
    void showJenisPembayaranError(String message);
    String getNoKartu();
    void showNoKartuError(String message);
    int getSelisih();
    Context getPesanWisataContext();
    void startPesanWisataFragment();
    void showPesanWisataError(String message);
}
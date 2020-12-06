package com.kelompokd.pbp_uas_a_keld.PesanPaketWisataTesting;

public class PesanWisataPresenter {

    private PesanWisataView view;
    private PesanWisataService service;
    private PesanWisataCallback callback;

    public PesanWisataPresenter(PesanWisataView view, PesanWisataService service) {
        this.view = view;
        this.service = service;
    }

    public void onCheckoutClicked() {
        ///////////////////////////////////////////////////////////////
        if(view.getNamaPesanan().isEmpty()){
            view.showNamaPesananError("Nama Pesanan Masih Kosong!");
            return;
        }
        else if(view.getImgPesanan().isEmpty()){
            view.showImgPesananError("Img Pesanan Masih Kosong!");
            return;
        }
        else if(view.getDurasiPesan().isEmpty()){
            view.showDurasiPesanError("Durasi Pesan Masih Kosong!");
            return;
        }
        else if(view.getTglPesan().isEmpty())
        {
            view.showTglPesanError("Tgl Pesan Masih Kosong!");
            return;
        }
        else if(view.getTglTransaksi().isEmpty())
        {
            view.showTglTransaksiError("Tgl Transaksi Masih Kosong!");
            return;
        }
        else if(view.getNama().isEmpty())
        {
            view.showNamaError("Nama Masih Kosong!");
            return;
        }
        else if(view.getEmail().isEmpty())
        {
            view.showEmailError("Email Masih Kosong!");
            return;
        }
        else if(view.getAlamat().isEmpty())
        {
            view.showAlamatError("Alamat Masih Kosong!");
            return;
        }
        else if(view.getJenisPembayaran().isEmpty())
        {
            view.showJenisPembayaranError("Jenis Pembayaran Masih Kosong!");
            return;
        }
        else
        {
            if(view.getJenisPembayaran().equals("Kartu Kredit")){
                if(view.getNoKartu().isEmpty()){
                    view.showNoKartuError("No Kartu Masih Kosong!");
                }
                else{
                    service.pesanWisata(view, view.getNamaPesanan(), view.getImgPesanan(), view.getDurasiPesan(), view.getTglPesan(),
                            view.getTglTransaksi(), view.getNama(), view.getEmail(), view.getAlamat(), view.getJenisPembayaran(), view.getNoKartu(), view.getSelisih(), 1,
                            new PesanWisataCallback() {
                                @Override
                                public void onSuccess(boolean value) {
                                    view.startPesanWisataFragment();
                                }
                                @Override
                                public void onError() {
                                }
                            });
                    return;
                }
            }
            else{
                service.pesanWisata(view, view.getNamaPesanan(), view.getImgPesanan(), view.getDurasiPesan(), view.getTglPesan(),
                        view.getTglTransaksi(), view.getNama(), view.getEmail(), view.getAlamat(), view.getJenisPembayaran(), null, view.getSelisih(), 1,
                        new PesanWisataCallback() {
                            @Override
                            public void onSuccess(boolean value) {
                                view.startPesanWisataFragment();
                            }
                            @Override
                            public void onError() {
                            }
                        });
                return;
            }
        }
    }
}


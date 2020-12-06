package com.kelompokd.pbp_uas_a_keld.UnitTestLogin;

import com.kelompokd.pbp_uas_a_keld.PesanPaketWisataTesting.PesanWisataCallback;
import com.kelompokd.pbp_uas_a_keld.PesanPaketWisataTesting.PesanWisataPresenter;
import com.kelompokd.pbp_uas_a_keld.PesanPaketWisataTesting.PesanWisataService;
import com.kelompokd.pbp_uas_a_keld.PesanPaketWisataTesting.PesanWisataView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PesanWisataPresenterTest {
    @Mock
    private PesanWisataView view;
    @Mock
    private PesanWisataService service;
    private PesanWisataPresenter presenter;
    @Before
    public void setUp() throws Exception {
        presenter = new PesanWisataPresenter(view, service);
    }

    @Test
    public void WhenNamaPesananEmpty() throws Exception
    {
        System.out.println("Test 1 - Nama Pesanan Empty");

        when(view.getNamaPesanan()).thenReturn("");
        System.out.println("Nama Pesanan       : " + view.getNamaPesanan());

        when(view.getImgPesanan()).thenReturn("https://www.javatravel.net/wp-content/uploads/2019/09/Pantai-Depok-Yogyakarta.jpg");
        System.out.println("Img Pesanan       : " + view.getImgPesanan());

        when(view.getDurasiPesan()).thenReturn("24");
        System.out.println("Durasi Pesan      : " + view.getDurasiPesan());

        when(view.getTglPesan()).thenReturn("2020/12/23");
        System.out.println("Tgl Pesan      : " + view.getTglPesan());

        when(view.getTglTransaksi()).thenReturn("2020/12/13");
        System.out.println("Tgl Transaksi      : " + view.getTglTransaksi());

        when(view.getNama()).thenReturn("Kelompok D");
        System.out.println("Nama      : " + view.getNama());

        when(view.getEmail()).thenReturn("yobelyuantomo@gmail.com");
        System.out.println("Email      : " + view.getEmail());

        when(view.getAlamat()).thenReturn("Jl Maju Mundur");
        System.out.println("Alamat      : " + view.getAlamat());

        when(view.getJenisPembayaran()).thenReturn("Kartu Kredit");
        System.out.println("Jenis Pembayaran      : " + view.getJenisPembayaran());

        when(view.getNoKartu()).thenReturn("12345678");
        System.out.println("No Kartu      : " + view.getNoKartu());

        presenter.onCheckoutClicked();
        verify(view).showNamaPesananError("Nama Pesanan Masih Kosong!");
    }

    @Test
    public void WhenImgPesananEmpty() throws Exception
    {
        System.out.println("Test 2 - Img Pesanan Empty");

        when(view.getNamaPesanan()).thenReturn("Wisata Pantai Pandansari");
        System.out.println("Nama Pesanan       : " + view.getNamaPesanan());

        when(view.getImgPesanan()).thenReturn("");
        System.out.println("Img Pesanan       : " + view.getImgPesanan());

        when(view.getDurasiPesan()).thenReturn("24");
        System.out.println("Durasi Pesan      : " + view.getDurasiPesan());

        when(view.getTglPesan()).thenReturn("2020/12/23");
        System.out.println("Tgl Pesan      : " + view.getTglPesan());

        when(view.getTglTransaksi()).thenReturn("2020/12/13");
        System.out.println("Tgl Transaksi      : " + view.getTglTransaksi());

        when(view.getNama()).thenReturn("Kelompok D");
        System.out.println("Nama      : " + view.getNama());

        when(view.getEmail()).thenReturn("yobelyuantomo@gmail.com");
        System.out.println("Email      : " + view.getEmail());

        when(view.getAlamat()).thenReturn("Jl Maju Mundur");
        System.out.println("Alamat      : " + view.getAlamat());

        when(view.getJenisPembayaran()).thenReturn("Kartu Kredit");
        System.out.println("Jenis Pembayaran      : " + view.getJenisPembayaran());

        when(view.getNoKartu()).thenReturn("12345678");
        System.out.println("No Kartu      : " + view.getNoKartu());

        presenter.onCheckoutClicked();
        verify(view).showImgPesananError("Img Pesanan Masih Kosong!");
    }

    @Test
    public void WhenDurasiPesanEmpty() throws Exception
    {
        System.out.println("Test 3 - Durasi Pesanan Empty");

        when(view.getNamaPesanan()).thenReturn("Wisata Pantai Pandansari");
        System.out.println("Nama Pesanan       : " + view.getNamaPesanan());

        when(view.getImgPesanan()).thenReturn("https://www.javatravel.net/wp-content/uploads/2019/09/Pantai-Depok-Yogyakarta.jpg");
        System.out.println("Img Pesanan       : " + view.getImgPesanan());

        when(view.getDurasiPesan()).thenReturn("");
        System.out.println("Durasi Pesan      : " + view.getDurasiPesan());

        when(view.getTglPesan()).thenReturn("2020/12/23");
        System.out.println("Tgl Pesan      : " + view.getTglPesan());

        when(view.getTglTransaksi()).thenReturn("2020/12/13");
        System.out.println("Tgl Transaksi      : " + view.getTglTransaksi());

        when(view.getNama()).thenReturn("Kelompok D");
        System.out.println("Nama      : " + view.getNama());

        when(view.getEmail()).thenReturn("yobelyuantomo@gmail.com");
        System.out.println("Email      : " + view.getEmail());

        when(view.getAlamat()).thenReturn("Jl Maju Mundur");
        System.out.println("Alamat      : " + view.getAlamat());

        when(view.getJenisPembayaran()).thenReturn("Kartu Kredit");
        System.out.println("Jenis Pembayaran      : " + view.getJenisPembayaran());

        when(view.getNoKartu()).thenReturn("12345678");
        System.out.println("No Kartu      : " + view.getNoKartu());

        presenter.onCheckoutClicked();
        verify(view).showDurasiPesanError("Durasi Pesan Masih Kosong!");
    }

    @Test
    public void WhenTglPesanEmpty() throws Exception
    {
        System.out.println("Test 4 - Tgl Pesan Empty");

        when(view.getNamaPesanan()).thenReturn("Wisata Pantai Pandansari");
        System.out.println("Nama Pesanan       : " + view.getNamaPesanan());

        when(view.getImgPesanan()).thenReturn("https://www.javatravel.net/wp-content/uploads/2019/09/Pantai-Depok-Yogyakarta.jpg");
        System.out.println("Img Pesanan       : " + view.getImgPesanan());

        when(view.getDurasiPesan()).thenReturn("24");
        System.out.println("Durasi Pesan      : " + view.getDurasiPesan());

        when(view.getTglPesan()).thenReturn("");
        System.out.println("Tgl Pesan      : " + view.getTglPesan());

        when(view.getTglTransaksi()).thenReturn("2020/12/13");
        System.out.println("Tgl Transaksi      : " + view.getTglTransaksi());

        when(view.getNama()).thenReturn("Kelompok D");
        System.out.println("Nama      : " + view.getNama());

        when(view.getEmail()).thenReturn("yobelyuantomo@gmail.com");
        System.out.println("Email      : " + view.getEmail());

        when(view.getAlamat()).thenReturn("Jl Maju Mundur");
        System.out.println("Alamat      : " + view.getAlamat());

        when(view.getJenisPembayaran()).thenReturn("Kartu Kredit");
        System.out.println("Jenis Pembayaran      : " + view.getJenisPembayaran());

        when(view.getNoKartu()).thenReturn("12345678");
        System.out.println("No Kartu      : " + view.getNoKartu());

        presenter.onCheckoutClicked();
        verify(view).showTglPesanError("Tgl Pesan Masih Kosong!");
    }

    @Test
    public void WhenTglTransaksiEmpty() throws Exception
    {
        System.out.println("Test 5 - Tgl Transaksi Empty");

        when(view.getNamaPesanan()).thenReturn("Wisata Pantai Pandansari");
        System.out.println("Nama Pesanan       : " + view.getNamaPesanan());

        when(view.getImgPesanan()).thenReturn("https://www.javatravel.net/wp-content/uploads/2019/09/Pantai-Depok-Yogyakarta.jpg");
        System.out.println("Img Pesanan       : " + view.getImgPesanan());

        when(view.getDurasiPesan()).thenReturn("24");
        System.out.println("Durasi Pesan      : " + view.getDurasiPesan());

        when(view.getTglPesan()).thenReturn("2020/12/23");
        System.out.println("Tgl Pesan      : " + view.getTglPesan());

        when(view.getTglTransaksi()).thenReturn("");
        System.out.println("Tgl Transaksi      : " + view.getTglTransaksi());

        when(view.getNama()).thenReturn("Kelompok D");
        System.out.println("Nama      : " + view.getNama());

        when(view.getEmail()).thenReturn("yobelyuantomo@gmail.com");
        System.out.println("Email      : " + view.getEmail());

        when(view.getAlamat()).thenReturn("Jl Maju Mundur");
        System.out.println("Alamat      : " + view.getAlamat());

        when(view.getJenisPembayaran()).thenReturn("Kartu Kredit");
        System.out.println("Jenis Pembayaran      : " + view.getJenisPembayaran());

        when(view.getNoKartu()).thenReturn("12345678");
        System.out.println("No Kartu      : " + view.getNoKartu());

        presenter.onCheckoutClicked();
        verify(view).showTglTransaksiError("Tgl Transaksi Masih Kosong!");
    }

    @Test
    public void WhenNamaEmpty() throws Exception
    {
        System.out.println("Test 6 - Nama Empty");

        when(view.getNamaPesanan()).thenReturn("Wisata Pantai Pandansari");
        System.out.println("Nama Pesanan       : " + view.getNamaPesanan());

        when(view.getImgPesanan()).thenReturn("https://www.javatravel.net/wp-content/uploads/2019/09/Pantai-Depok-Yogyakarta.jpg");
        System.out.println("Img Pesanan       : " + view.getImgPesanan());

        when(view.getDurasiPesan()).thenReturn("24");
        System.out.println("Durasi Pesan      : " + view.getDurasiPesan());

        when(view.getTglPesan()).thenReturn("2020/12/23");
        System.out.println("Tgl Pesan      : " + view.getTglPesan());

        when(view.getTglTransaksi()).thenReturn("2020/12/13");
        System.out.println("Tgl Transaksi      : " + view.getTglTransaksi());

        when(view.getNama()).thenReturn("");
        System.out.println("Nama      : " + view.getNama());

        when(view.getEmail()).thenReturn("yobelyuantomo@gmail.com");
        System.out.println("Email      : " + view.getEmail());

        when(view.getAlamat()).thenReturn("Jl Maju Mundur");
        System.out.println("Alamat      : " + view.getAlamat());

        when(view.getJenisPembayaran()).thenReturn("Kartu Kredit");
        System.out.println("Jenis Pembayaran      : " + view.getJenisPembayaran());

        when(view.getNoKartu()).thenReturn("12345678");
        System.out.println("No Kartu      : " + view.getNoKartu());

        presenter.onCheckoutClicked();
        verify(view).showNamaError("Nama Masih Kosong!");
    }

    @Test
    public void WhenEmailPesanEmpty() throws Exception
    {
        System.out.println("Test 7 - Email Empty");

        when(view.getNamaPesanan()).thenReturn("Wisata Pantai Pandansari");
        System.out.println("Nama Pesanan       : " + view.getNamaPesanan());

        when(view.getImgPesanan()).thenReturn("https://www.javatravel.net/wp-content/uploads/2019/09/Pantai-Depok-Yogyakarta.jpg");
        System.out.println("Img Pesanan       : " + view.getImgPesanan());

        when(view.getDurasiPesan()).thenReturn("24");
        System.out.println("Durasi Pesan      : " + view.getDurasiPesan());

        when(view.getTglPesan()).thenReturn("2020/12/23");
        System.out.println("Tgl Pesan      : " + view.getTglPesan());

        when(view.getTglTransaksi()).thenReturn("2020/12/13");
        System.out.println("Tgl Transaksi      : " + view.getTglTransaksi());

        when(view.getNama()).thenReturn("Kelompok D");
        System.out.println("Nama      : " + view.getNama());

        when(view.getEmail()).thenReturn("");
        System.out.println("Email      : " + view.getEmail());

        when(view.getAlamat()).thenReturn("Jl Maju Mundur");
        System.out.println("Alamat      : " + view.getAlamat());

        when(view.getJenisPembayaran()).thenReturn("Kartu Kredit");
        System.out.println("Jenis Pembayaran      : " + view.getJenisPembayaran());

        when(view.getNoKartu()).thenReturn("12345678");
        System.out.println("No Kartu      : " + view.getNoKartu());

        presenter.onCheckoutClicked();
        verify(view).showEmailError("Email Masih Kosong!");
    }

    @Test
    public void WhenAlamatEmpty() throws Exception
    {
        System.out.println("Test 8 - Alamat Empty");

        when(view.getNamaPesanan()).thenReturn("Wisata Pantai Pandansari");
        System.out.println("Nama Pesanan       : " + view.getNamaPesanan());

        when(view.getImgPesanan()).thenReturn("https://www.javatravel.net/wp-content/uploads/2019/09/Pantai-Depok-Yogyakarta.jpg");
        System.out.println("Img Pesanan       : " + view.getImgPesanan());

        when(view.getDurasiPesan()).thenReturn("24");
        System.out.println("Durasi Pesan      : " + view.getDurasiPesan());

        when(view.getTglPesan()).thenReturn("2020/12/23");
        System.out.println("Tgl Pesan      : " + view.getTglPesan());

        when(view.getTglTransaksi()).thenReturn("2020/12/13");
        System.out.println("Tgl Transaksi      : " + view.getTglTransaksi());

        when(view.getNama()).thenReturn("Kelompok D");
        System.out.println("Nama      : " + view.getNama());

        when(view.getEmail()).thenReturn("yobelyuantomo@gmail.com");
        System.out.println("Email      : " + view.getEmail());

        when(view.getAlamat()).thenReturn("");
        System.out.println("Alamat      : " + view.getAlamat());

        when(view.getJenisPembayaran()).thenReturn("Kartu Kredit");
        System.out.println("Jenis Pembayaran      : " + view.getJenisPembayaran());

        when(view.getNoKartu()).thenReturn("12345678");
        System.out.println("No Kartu      : " + view.getNoKartu());

        presenter.onCheckoutClicked();
        verify(view).showAlamatError("Alamat Masih Kosong!");
    }

    @Test
    public void WhenJenisPembayaranEmpty() throws Exception
    {
        System.out.println("Test 9 - Jenis Pembayaran Empty");

        when(view.getNamaPesanan()).thenReturn("Wisata Pantai Pandansari");
        System.out.println("Nama Pesanan       : " + view.getNamaPesanan());

        when(view.getImgPesanan()).thenReturn("https://www.javatravel.net/wp-content/uploads/2019/09/Pantai-Depok-Yogyakarta.jpg");
        System.out.println("Img Pesanan       : " + view.getImgPesanan());

        when(view.getDurasiPesan()).thenReturn("24");
        System.out.println("Durasi Pesan      : " + view.getDurasiPesan());

        when(view.getTglPesan()).thenReturn("2020/12/23");
        System.out.println("Tgl Pesan      : " + view.getTglPesan());

        when(view.getTglTransaksi()).thenReturn("2020/12/13");
        System.out.println("Tgl Transaksi      : " + view.getTglTransaksi());

        when(view.getNama()).thenReturn("Kelompok D");
        System.out.println("Nama      : " + view.getNama());

        when(view.getEmail()).thenReturn("yobelyuantomo@gmail.com");
        System.out.println("Email      : " + view.getEmail());

        when(view.getAlamat()).thenReturn("Jl Maju Mundur");
        System.out.println("Alamat      : " + view.getAlamat());

        when(view.getJenisPembayaran()).thenReturn("");
        System.out.println("Jenis Pembayaran      : " + view.getJenisPembayaran());

        when(view.getNoKartu()).thenReturn("12345678");
        System.out.println("No Kartu      : " + view.getNoKartu());

        presenter.onCheckoutClicked();
        verify(view).showJenisPembayaranError("Jenis Pembayaran Masih Kosong!");
    }

    @Test
    public void WhenNokartu1Empty() throws Exception
    {
        System.out.println("Test 10 - No Kartu Dengan Pembayaran Tunai Empty");

        when(view.getNamaPesanan()).thenReturn("Wisata Pantai Pandansari");
        System.out.println("Nama Pesanan       : " + view.getNamaPesanan());

        when(view.getImgPesanan()).thenReturn("https://www.javatravel.net/wp-content/uploads/2019/09/Pantai-Depok-Yogyakarta.jpg");
        System.out.println("Img Pesanan       : " + view.getImgPesanan());

        when(view.getDurasiPesan()).thenReturn("24");
        System.out.println("Durasi Pesan      : " + view.getDurasiPesan());

        when(view.getTglPesan()).thenReturn("2020/12/23");
        System.out.println("Tgl Pesan      : " + view.getTglPesan());

        when(view.getTglTransaksi()).thenReturn("2020/12/13");
        System.out.println("Tgl Transaksi      : " + view.getTglTransaksi());

        when(view.getNama()).thenReturn("Kelompok D");
        System.out.println("Nama      : " + view.getNama());

        when(view.getEmail()).thenReturn("yobelyuantomo@gmail.com");
        System.out.println("Email      : " + view.getEmail());

        when(view.getAlamat()).thenReturn("Jl Maju Mundur");
        System.out.println("Alamat      : " + view.getAlamat());

        when(view.getJenisPembayaran()).thenReturn("Tunai");
        System.out.println("Jenis Pembayaran      : " + view.getJenisPembayaran());

        when(view.getNoKartu()).thenReturn("");
        System.out.println("No Kartu      : " + view.getNoKartu());

        when(service.getValid(view, view.getNamaPesanan(), view.getImgPesanan(), view.getDurasiPesan(),
            view.getTglPesan(), view.getTglTransaksi(), view.getNama(), view.getEmail(), view.getAlamat(),
                view.getJenisPembayaran(), view.getNoKartu(), view.getSelisih(), 1)).thenReturn(true);

        System.out.println("Result       : " + service.getValid(view, view.getNamaPesanan(), view.getImgPesanan(), view.getDurasiPesan(),
                view.getTglPesan(), view.getTglTransaksi(), view.getNama(), view.getEmail(), view.getAlamat(),
                view.getJenisPembayaran(), view.getNoKartu(), view.getSelisih(), 1));
        presenter.onCheckoutClicked();
    }

    @Test
    public void WhenNokartu2Empty() throws Exception
    {
        System.out.println("Test 11 - No Kartu Dengan Pembayaran Kartu Kredit Empty");

        when(view.getNamaPesanan()).thenReturn("Wisata Pantai Pandansari");
        System.out.println("Nama Pesanan       : " + view.getNamaPesanan());

        when(view.getImgPesanan()).thenReturn("https://www.javatravel.net/wp-content/uploads/2019/09/Pantai-Depok-Yogyakarta.jpg");
        System.out.println("Img Pesanan       : " + view.getImgPesanan());

        when(view.getDurasiPesan()).thenReturn("24");
        System.out.println("Durasi Pesan      : " + view.getDurasiPesan());

        when(view.getTglPesan()).thenReturn("2020/12/23");
        System.out.println("Tgl Pesan      : " + view.getTglPesan());

        when(view.getTglTransaksi()).thenReturn("2020/12/13");
        System.out.println("Tgl Transaksi      : " + view.getTglTransaksi());

        when(view.getNama()).thenReturn("Kelompok D");
        System.out.println("Nama      : " + view.getNama());

        when(view.getEmail()).thenReturn("yobelyuantomo@gmail.com");
        System.out.println("Email      : " + view.getEmail());

        when(view.getAlamat()).thenReturn("Jl Maju Mundur");
        System.out.println("Alamat      : " + view.getAlamat());

        when(view.getJenisPembayaran()).thenReturn("Kartu Kredit");
        System.out.println("Jenis Pembayaran      : " + view.getJenisPembayaran());

        when(view.getNoKartu()).thenReturn("");
        System.out.println("No Kartu      : " + view.getNoKartu());

        presenter.onCheckoutClicked();
        verify(view).showNoKartuError("No Kartu Masih Kosong!");
    }
}
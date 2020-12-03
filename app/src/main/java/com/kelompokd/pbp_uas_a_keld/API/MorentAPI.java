package com.kelompokd.pbp_uas_a_keld.API;

public class MorentAPI {
    public static final String ROOT_URL = "https://morent.apitubespbp.xyz/";
    public static final String ROOT_API = ROOT_URL + "api/";
    public static final String URL_LOGIN = ROOT_API + "login";
    public static final String URL_RESEND = ROOT_API + "email/resend/";
    public static final String URL_REGISTER = ROOT_API + "register";
    public static final String URL_REFRESH = ROOT_API + "refresh";

    //UserController
    public static final String URL_SELECT_USER = ROOT_API + "user";
    public static final String URL_SHOW_USER = ROOT_API + "user/";
    public static final String URL_ADD_USER = ROOT_API + "user";
    public static final String URL_UPDATE_USER = ROOT_API + "user/";
    public static final String URL_DELETE_USER = ROOT_API + "deleteUser/";

    //PesanController
    public static final String URL_SELECT_PESANAN = ROOT_API + "pesanan/";
    public static final String URL_SHOW_PESANAN = ROOT_API + "pesanan/";
    public static final String URL_ADD_PESANAN = ROOT_API + "pesanan";
    public static final String URL_UPDATE_PESANAN = ROOT_API + "pesanan/";
    public static final String URL_DELETE_PESANAN = ROOT_API + "deletePesanan/";

    //MobilController
    public static final String URL_SELECT_MOBIL = ROOT_API + "mobil";
    public static final String URL_SHOW_MOBIL = ROOT_API + "mobil/";
    public static final String URL_ADD_MOBIL = ROOT_API + "mobil";
    public static final String URL_UPDATE_MOBIL = ROOT_API + "mobil/";
    public static final String URL_DELETE_MOBIL = ROOT_API + "deleteMobil/";

    //GetImageMobil
    public static final String URL_ALL_IMAGE_MOBIL = ROOT_API + "getAllImageMobil";
    public static final String URL_IMAGE_MOBIL = ROOT_API + "getImageMobil/";

    //WisataController
    public static final String URL_SELECT_WISATA = ROOT_API + "wisata";
    public static final String URL_SHOW_WISATA = ROOT_API + "wisata/";
    public static final String URL_ADD_WISATA = ROOT_API + "wisata";
    public static final String URL_UPDATE_WISATA = ROOT_API + "wisata/";
    public static final String URL_DELETE_WISATA = ROOT_API + "deleteWisata/";

    //GetImageWisata
    public static final String URL_ALL_IMAGE_WISATA = ROOT_API + "getAllImageWisata";
    public static final String URL_IMAGE_WISATA = ROOT_API + "getImageWisata/";
}
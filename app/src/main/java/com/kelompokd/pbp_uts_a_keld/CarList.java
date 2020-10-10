package com.kelompokd.pbp_uts_a_keld;

import java.util.ArrayList;

public class CarList {
    public ArrayList<Car> CAR;

    public CarList(){
        CAR = new ArrayList();
        CAR.add(AVANZA);
        CAR.add(ALL_NEW_AVANZA);
        CAR.add(INNOVA);
        CAR.add(INNOVA_REBORN);
        CAR.add(CAMRY);
        CAR.add(FORTUNER);
        CAR.add(ALPHARD);
        CAR.add(STRADA);
    }

    public static final Car AVANZA = new Car("Avanza", "Rp. 350.000", "12 Jam", "Mobil & Supir", "7", "https://cdn.shortpixel.ai/client/q_lossy,ret_img,w_300,h_171/https://www.ghaisanrental.com/images/2017/11/sewa-mobil-avanza-di-padang-300x171.jpg");
    public static final Car ALL_NEW_AVANZA = new Car("All New Avanza", "Rp. 400.000", "12 Jam", "Mobil & Supir", "7", "https://cdn.shortpixel.ai/client/q_lossy,ret_img,w_300,h_169/https://www.ghaisanrental.com/images/2017/11/sewa-mobil-all-new-avanza-Padang-300x169.jpg");
    public static final Car INNOVA = new Car("Innova", "Rp. 450.000", "12 Jam", "Mobil & Supir", "8", "https://cdn.shortpixel.ai/client/q_lossy,ret_img,w_260,h_161/https://www.ghaisanrental.com/images/2017/11/rental-mobil-innova-di-Padang-300x186.jpg");
    public static final Car INNOVA_REBORN = new Car("Innova Reborn", "Rp. 550.000", "12 Jam", "Mobil & Supir", "8", "https://cdn.shortpixel.ai/client/q_lossy,ret_img,w_250,h_155/https://www.ghaisanrental.com/images/2017/11/sewa-mobil-innova-reborn-di-padang-300x186.jpg");
    public static final Car CAMRY = new Car("Camry", "Rp. 1.400.000", "12 Jam", "Mobil & Supir", "4", "https://cdn.shortpixel.ai/client/q_lossy,ret_img,w_259,h_131/https://www.ghaisanrental.com/images/2017/11/Sewa-Toyota-Camry-di-Padang-300x152.jpg");
    public static final Car FORTUNER = new Car("Fortuner", "Rp. 1.200.000", "12 Jam", "Mobil & Supir", "7", "https://cdn.shortpixel.ai/client/q_lossy,ret_img,w_260,h_163/https://www.ghaisanrental.com/images/2017/11/rental-mobil-fortuner-di-padang-300x188.jpg");
    public static final Car ALPHARD = new Car("Alphard", "Rp. 2.500.000", "12 Jam", "Mobil & Supir", "8", "https://cdn.shortpixel.ai/client/q_lossy,ret_img,w_260,h_138/https://www.ghaisanrental.com/images/2017/11/rental-mobil-Alphard-di-Padang-300x159.jpg");
    public static final Car STRADA = new Car("Strada Triton", "Rp. 1.500.000", "12 Jam", "Mobil & Supir", "2", "https://cdn.shortpixel.ai/client/q_lossy,ret_img,w_261,h_159/https://www.ghaisanrental.com/images/2017/11/rental-mobil-strada-di-padang-300x183.jpg");
}

package com.kreators.crtoolv1.Model;

/**
 * Created by Julio Anthony Leonar on 8/12/2016.
 */
public class IndoCurrencyFormat {

    public static String transformIntegerToRupiah (long money) {
        String rupiah = "Rp. ";
        String formattedNumber = String.format("%,d", money);
        rupiah+= formattedNumber;
        return rupiah;
    }
}

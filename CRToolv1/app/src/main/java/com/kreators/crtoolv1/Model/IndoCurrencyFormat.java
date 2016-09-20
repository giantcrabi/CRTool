package com.kreators.crtoolv1.Model;

/**
 * Created by Julio Anthony Leonar on 8/12/2016.
 */
public class IndoCurrencyFormat {

    public static String transformIntegerToRupiah (long money) {
        String rupiah = "Rp. ";
        String result = String.format("%,d", money);
        rupiah+= result;
        return rupiah;
    }

    public static String parseByThousand (long number) {
        String result = String.format("%,d", number);
        return result;
    }

    public static String percentage (double number) {
        String result = String.format("%.2f", number);
        return result;
    }
}

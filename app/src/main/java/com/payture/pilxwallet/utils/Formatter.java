package com.payture.pilxwallet.utils;

/**
 * Created by simpleman383 on 21.11.17.
 */

public class Formatter {

    public static String toCurrencyFormat(double number) {
        return String.format("%.2f", number).replace(',', '.');
    }

    public static String toBTCFormat(double number) {
        return String.format("%.9f", number).replace(',', '.');
    }

}

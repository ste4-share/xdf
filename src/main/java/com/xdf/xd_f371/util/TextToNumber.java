package com.xdf.xd_f371.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class TextToNumber {

    public static String textToNum(String text) {
            Locale locale = new Locale("vi", "VN");
            Currency currency = Currency.getInstance("VND");

            DecimalFormat numberFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(locale);
            DecimalFormatSymbols symbols = numberFormat.getDecimalFormatSymbols();
            symbols.setCurrencySymbol("");
            numberFormat.setDecimalFormatSymbols(symbols);
            numberFormat.setCurrency(currency);
            numberFormat.setMaximumFractionDigits(0);
            return numberFormat.format(Double.parseDouble(text));
    }
    public static String textToNum_2digits(double d){
        Locale locale = new Locale("vi", "VN");
        Currency currency = Currency.getInstance("VND");

        DecimalFormat numberFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(locale);
        DecimalFormatSymbols symbols = numberFormat.getDecimalFormatSymbols();
        symbols.setCurrencySymbol("");
        numberFormat.setDecimalFormatSymbols(symbols);
        numberFormat.setCurrency(currency);
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat.format(d);
    }
}

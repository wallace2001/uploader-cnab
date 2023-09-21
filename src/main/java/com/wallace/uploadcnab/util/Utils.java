package com.wallace.uploadcnab.util;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;

@Component
public class Utils {
    public static String formatDocumento(String documento) {
        return documento.substring(0, 3) + "." +
                documento.substring(3, 6) + "." +
                documento.substring(6, 9) + "-" +
                documento.substring(9, 11);
    }

    public static String formatCardNumber(String cardNumber) {
        return cardNumber.substring(0, 4) + "-" +
                cardNumber.substring(4, 8) + "-" +
                cardNumber.substring(8, 12);
    }

    public static String formatValue(String value) {
        double valorNumerico = Double.parseDouble(value) / 100;
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        return formatoMoeda.format(valorNumerico);
    }
    public static String formatTimestamp(Timestamp timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        return dateFormat.format(timestamp);
    }

    public static String randomStringSimple(int length) {
        String alphanumericCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuv";

        StringBuffer randomString = new StringBuffer(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(alphanumericCharacters.length());
            char randomChar = alphanumericCharacters.charAt(randomIndex);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }
}

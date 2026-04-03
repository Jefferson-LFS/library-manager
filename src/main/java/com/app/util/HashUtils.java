package com.app.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {
    public static String criarMD5(String senha){

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hasBytes = md.digest(senha.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hasBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

}

package com.shel.renter.vaadin.util;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class PasswordCrypto {
    public static String encryptPassword(String originalString) {
        final MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA3-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        final byte[] bytes = digest.digest(
                originalString.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(bytes);
    }

    public static boolean checkPassword(String inputPassword, String passwordFromDb) {
        String inputPass = encryptPassword(inputPassword);
        log.info("Expected password was {}", inputPass);
        return inputPass.equals(passwordFromDb);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}

package com.example.servicesbcrypt.utils;

import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.util.Base64;

import java.security.Key;


public class Encrypt {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static  String KEY = "el@ConGr3z041!12"; // La clave need ser de 16 bytes


    public static String encrypt(String value) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        byte[] initializationVector = new byte[16]; // AES utiliza bloques de 16 bytes
        new SecureRandom().nextBytes(initializationVector);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initializationVector);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        byte[] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
        byte[] encryptedValueWithIv = new byte[initializationVector.length + encryptedByteValue.length];
        System.arraycopy(initializationVector, 0, encryptedValueWithIv, 0, initializationVector.length);
        System.arraycopy(encryptedByteValue, 0, encryptedValueWithIv, initializationVector.length, encryptedByteValue.length);
        return Base64.getEncoder().encodeToString(encryptedValueWithIv);
    }

    public static String decrypt(String value) throws Exception {
        String urlDecode = URLDecoder.decode(value.replaceAll("\\+","%2B"), "UTF-8");
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        byte[] decodedValue = Base64.getDecoder().decode(urlDecode);
        byte[] iv = new byte[16];
        System.arraycopy(decodedValue, 0, iv, 0, iv.length);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        byte[] decryptedByteValue = cipher.doFinal(decodedValue, iv.length, decodedValue.length - iv.length);
        return new String(decryptedByteValue, "utf-8");
    }

    private static Key generateKey() throws Exception {
        return new SecretKeySpec(KEY.getBytes(), ALGORITHM);
    }
}

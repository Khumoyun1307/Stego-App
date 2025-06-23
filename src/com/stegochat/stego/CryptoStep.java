package com.stegochat.stego;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class CryptoStep implements StegoStep {
    private static final int SALT_LENGTH = 16;
    private static final int IV_LENGTH = 16;
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;

    private final String password;

    public CryptoStep(String password) {
        this.password = password;
    }

    @Override
    public String encode(String input) {
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secret = new SecretKeySpec(tmp.getEncoded(), "AES");
            byte[] iv = new byte[IV_LENGTH];
            random.nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret, ivSpec);
            byte[] ciphertext = cipher.doFinal(input.getBytes("UTF-8"));
            String b64salt = Base64.getEncoder().encodeToString(salt);
            String b64iv   = Base64.getEncoder().encodeToString(iv);
            String b64ct   = Base64.getEncoder().encodeToString(ciphertext);
            return b64salt + ":" + b64iv + ":" + b64ct;
        } catch (Exception e) {
            throw new RuntimeException("Encryption error", e);
        }
    }

    @Override
    public String decode(String input) {
        try {
            String[] parts = input.split(":");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid encrypted format");
            }
            byte[] salt       = Base64.getDecoder().decode(parts[0]);
            byte[] iv         = Base64.getDecoder().decode(parts[1]);
            byte[] ciphertext = Base64.getDecoder().decode(parts[2]);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secret = new SecretKeySpec(tmp.getEncoded(), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secret, ivSpec);
            byte[] plaintext = cipher.doFinal(ciphertext);
            return new String(plaintext, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("Decryption error", e);
        }
    }
}

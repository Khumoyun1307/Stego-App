package com.stegochat.auth;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * Utility for hashing and verifying user passwords using PBKDF2.
 */
public class PasswordHasher {
    private static final int SALT_LENGTH = 16;
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;

    /**
     * Hashes a plaintext password, returning salt:hash in Base64.
     */
    public static String hash(String password) {
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);

            byte[] hash = pbkdf2(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            String b64Salt = Base64.getEncoder().encodeToString(salt);
            String b64Hash = Base64.getEncoder().encodeToString(hash);
            return b64Salt + ":" + b64Hash;
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /**
     * Verifies a plaintext password against a stored salt:hash string.
     */
    public static boolean verify(String password, String stored) {
        try {
            String[] parts = stored.split(":");
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] hash = Base64.getDecoder().decode(parts[1]);

            byte[] testHash = pbkdf2(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            return constantTimeEquals(hash, testHash);
        } catch (Exception e) {
            return false;
        }
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iter, int keyLen) throws Exception {
        KeySpec spec = new PBEKeySpec(password, salt, iter, keyLen);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return skf.generateSecret(spec).getEncoded();
    }

    private static boolean constantTimeEquals(byte[] a, byte[] b) {
        if (a.length != b.length) return false;
        int result = 0;
        for (int i = 0; i < a.length; i++) {
            result |= a[i] ^ b[i];
        }
        return result == 0;
    }
}


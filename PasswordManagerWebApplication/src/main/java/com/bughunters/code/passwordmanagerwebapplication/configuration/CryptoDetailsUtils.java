package com.bughunters.code.passwordmanagerwebapplication.configuration;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Component

public class CryptoDetailsUtils {
    private SecretKey secretKey;
    private static final int T_LEN = 128; // Tag length for GCM
    private static final int IV_SIZE = 12; // IV size for GCM, 12 bytes is the recommended size
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    // Method to initialize and generate a secret key
    public SecretKey init() throws Exception {
        int size = 256;
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(size);
        secretKey = generator.generateKey();
        return secretKey;
    }

    // Method to encrypt a message
    public String encrypt(String message) throws Exception {
        byte[] messageBytes = message.getBytes();
        Cipher encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");

        // Generate a new IV for each encryption
        byte[] iv = new byte[IV_SIZE];
        SECURE_RANDOM.nextBytes(iv);
        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, iv);

        encryptionCipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
        byte[] encryptedBytes = encryptionCipher.doFinal(messageBytes);

        // Combine IV and encrypted bytes
        byte[] encryptedMessageWithIv = new byte[IV_SIZE + encryptedBytes.length];
        System.arraycopy(iv, 0, encryptedMessageWithIv, 0, IV_SIZE);
        System.arraycopy(encryptedBytes, 0, encryptedMessageWithIv, IV_SIZE, encryptedBytes.length);

        return encode(encryptedMessageWithIv);
    }

    // Method to decrypt an encrypted message
    public String decrypt(String encryptedMessage) throws Exception {
        byte[] encryptedMessageBytes = decode(encryptedMessage);

        // Extract IV from the beginning of the encrypted message
        byte[] iv = new byte[IV_SIZE];
        System.arraycopy(encryptedMessageBytes, 0, iv, 0, IV_SIZE);

        byte[] encryptedBytes = new byte[encryptedMessageBytes.length - IV_SIZE];
        System.arraycopy(encryptedMessageBytes, IV_SIZE, encryptedBytes, 0, encryptedBytes.length);

        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, iv);
        decryptionCipher.init(Cipher.DECRYPT_MODE, secretKey, spec);

        byte[] decryptedBytes = decryptionCipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

    // Method to encode bytes to a Base64 string
    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    // Method to decode a Base64 string to bytes
    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    // Method to initialize the secret key from a Base64 encoded string
    public void initFromStrings(String key) {
        secretKey = new SecretKeySpec(decode(key), "AES");
        // Note: the IV is no longer needed here since it's generated during encryption
    }
}


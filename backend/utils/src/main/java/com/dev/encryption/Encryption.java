package com.dev.encryption;

import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;


/**
 * This class provides methods for encryption.
 */
public class Encryption {

    /**
     * Generate a 16 bytes salt for encryption.
     *
     * @return salt
     */
    public static String saltGenerater() {
        String chars = "abcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()_+";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            builder.append(chars.charAt(new Random().nextInt(chars.length())));
        }
        return builder.toString();
    }

    /**
     * Encrypt a string with md5.
     *
     * @param password the string to be encrypted
     * @return the md5 hex code string
     */
    public static String md5(String password, String salt) {
        return DigestUtils
                .md5Hex(password + salt).toUpperCase();
    }
}

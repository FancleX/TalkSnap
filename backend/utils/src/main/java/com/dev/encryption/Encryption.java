package com.dev.encryption;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5 encryption.
 */
public class Encryption {

    /**
     * Encrypt a string with md5.
     *
     * @param target the string to be encrypted
     * @return the md5 hex code string
     */
    public static String md5(String target) {
        return DigestUtils
                .md5Hex(target).toUpperCase();
    }
}

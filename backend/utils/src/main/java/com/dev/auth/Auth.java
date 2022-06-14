package com.dev.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Authorization helper.
 */
public class Auth {

    // one week expired time
    private static final long EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000;

    // private key
    private static final String KEY = "dafsdvbzeewrvcz56432!@*#$";

    /**
     * Generate a token with payload userId and username.
     *
     * @param userId   id
     * @param username nickname
     * @return signed token
     */
    public static String generateToken(Long userId, String username) {
        // get time and set expire time
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        // set token algorithm
        Algorithm algorithm = Algorithm.HMAC256(KEY);
        // set token header
        Map<String, Object> header = new HashMap<>(2);
        header.put("type", "jwt");
        header.put("algorithm", "HMAC256");
        // return token
        return JWT.create().withHeader(header)
                // add payload
                .withClaim("userId", userId)
                .withClaim("nickname", username)
                // set expire time
                .withExpiresAt(date)
                // signed algorithm
                .sign(algorithm);
    }

    /**
     * Verify if the token is valid.
     *
     * @param token token to be verified
     * @return true if the token is valid, otherwise false
     */
    public static boolean verify(String token) {
        try {
            // get algorithm
            Algorithm algorithm = Algorithm.HMAC256(KEY);
            // get verifier
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            // verify token
            DecodedJWT verify = jwtVerifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

}

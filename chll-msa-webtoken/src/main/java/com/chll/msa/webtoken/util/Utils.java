package com.chll.msa.webtoken.util;

import java.security.SecureRandom;

public class Utils {

    private Utils(){}

    private static final SecureRandom random = new SecureRandom();
    private static final int TOKEN_SIZE = 6;

    public static String generateToken() {
        StringBuilder token = new StringBuilder();

        for (int i = 0; i < TOKEN_SIZE; i++) {
            token.append(random.nextInt(10));
        }
        return token.toString();
    }
}

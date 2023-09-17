package com.chamasoft;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PassEncryption {

    public static String getSecuredPassword(String password) {
        // Generate a salt and hash the password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);
        return hashedPassword;
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, hashedPassword);
    }

}

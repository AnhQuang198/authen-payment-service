package com.example.authenpaymentservice.authen.utils;

import java.util.Base64;
import java.util.Random;

public class Common {
    public static int generateOTP() {
        Random r = new Random(System.currentTimeMillis());
        return ((1 + r.nextInt(2)) * 100000 + r.nextInt(100000));
    }

    public static String encode(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    public static String decode(String str) {
        return new String(Base64.getDecoder().decode(str));
    }
}

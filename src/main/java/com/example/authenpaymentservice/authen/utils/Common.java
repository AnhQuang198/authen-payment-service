package com.example.authenpaymentservice.authen.utils;

import java.util.Random;

public class Common {
    public static int generateOTP() {
        Random r = new Random(System.currentTimeMillis());
        return ((1 + r.nextInt(2)) * 100000 + r.nextInt(100000));
    }
}

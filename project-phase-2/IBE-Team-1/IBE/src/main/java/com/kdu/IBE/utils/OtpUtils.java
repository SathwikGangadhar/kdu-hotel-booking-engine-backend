package com.kdu.IBE.utils;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OtpUtils {
    public int getOtp(){
        Random random = new Random();
        int number = random.nextInt(900000) + 100000;
        return number;
    }
}

package com.kdu.IBE.utils;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OtpUtils {
    public int getOtp(){
        Random random = new Random();
        int number = random.nextInt(9000) + 1000;
        return number;
    }
}

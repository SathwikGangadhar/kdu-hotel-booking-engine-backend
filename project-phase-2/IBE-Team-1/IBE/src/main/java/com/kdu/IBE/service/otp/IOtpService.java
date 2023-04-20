package com.kdu.IBE.service.otp;

import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface IOtpService {
    public ResponseEntity<String> setOtp(String bookingId,String receiverEmail);
    public ResponseEntity<String> putOtp(String otp,String bookingId);
    public ResponseEntity<String> putOtpForLoggedInUser(String bookingId);

    }

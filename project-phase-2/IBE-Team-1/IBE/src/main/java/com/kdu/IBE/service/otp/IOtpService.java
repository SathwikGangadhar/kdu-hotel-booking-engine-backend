package com.kdu.IBE.service.otp;

import org.springframework.http.ResponseEntity;

public interface IOtpService {
    public ResponseEntity<Integer> setOtp(String bookingId,String receiverEmail);
    public ResponseEntity<String> putOtp(String otp,String bookingId);
}

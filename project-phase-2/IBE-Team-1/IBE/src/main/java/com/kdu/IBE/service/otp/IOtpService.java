package com.kdu.IBE.service.otp;

import org.springframework.http.ResponseEntity;

public interface IOtpService {
    public ResponseEntity<?> setOtp(String bookingId,String receiverEmail);
    public ResponseEntity<?> putOtp(String otp,String bookingId);
}

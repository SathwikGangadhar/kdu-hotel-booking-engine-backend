package com.kdu.IBE.service.referral;

import com.kdu.IBE.model.requestDto.ReferralDetailsRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface IReferralService {
    ResponseEntity<String > putReferralCode(String userEmail, String referralCode);
    ResponseEntity<Boolean> isReferralCodeExists(String referralCode);
    ResponseEntity<String> addReferralDetails(ReferralDetailsRequestDto referralDetailsRequestDto, BindingResult result);
    }




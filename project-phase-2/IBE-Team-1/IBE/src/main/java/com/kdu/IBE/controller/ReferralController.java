package com.kdu.IBE.controller;

import com.kdu.IBE.constants.EndPointConstants;
import com.kdu.IBE.model.requestDto.ReferralDetailsRequestDto;
import com.kdu.IBE.service.referral.IReferralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@RestController
@RequestMapping(EndPointConstants.REFERRAL_REQUEST_MAPPING)
public class ReferralController {
    @Autowired
    public IReferralService referralService;
    @PostMapping(EndPointConstants.PUT_REFERRAL)
    ResponseEntity<String> putReferralCode(@RequestParam(name = "user_email") String userEmail,@RequestParam(name = "referral_code")String referralCode){
        return referralService.putReferralCode(userEmail,referralCode);
    }
    @GetMapping("/is/referral/present")
    ResponseEntity<Boolean> isReferralPresent(@RequestParam("referral_code") String referralCode){
        return referralService.isReferralCodeExists(referralCode);
    }
    @PostMapping("put/referral-user-details")
    ResponseEntity<String> putReferralUserDetails(@Valid @RequestBody ReferralDetailsRequestDto referralDetailsRequestDto, BindingResult result){
        return referralService.addReferralDetails(referralDetailsRequestDto,result);
    }

}

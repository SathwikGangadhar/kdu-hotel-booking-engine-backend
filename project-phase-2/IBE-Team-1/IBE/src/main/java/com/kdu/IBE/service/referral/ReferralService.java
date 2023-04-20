package com.kdu.IBE.service.referral;

import com.kdu.IBE.entity.Referral;
import com.kdu.IBE.entity.ReferralDetails;
import com.kdu.IBE.exception.UniqueConstraintException;
import com.kdu.IBE.model.requestDto.ReferralDetailsRequestDto;
import com.kdu.IBE.repository.ReferralDetailsRepository;
import com.kdu.IBE.repository.ReferralRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class ReferralService implements IReferralService{
    @Autowired
    public ReferralRepository referralRepository;
    @Autowired
    public ReferralDetailsRepository referralDetailsRepository;

    /**
     * @param userEmail
     * @param referralCode
     * @return
     */
    public ResponseEntity<String > putReferralCode(String userEmail,String referralCode){
        Referral referral=Referral.builder()
                .userEmail(userEmail)
                .referralCode(referralCode)
                .build();
        try {
            referralRepository.save(referral);
        }catch (RuntimeException exception){
            throw new UniqueConstraintException("Referral Code all ready Exists");
        }
        return new ResponseEntity<String>("Referral Code added successfully", HttpStatus.OK);
    }

    /**
     * @param referralCode
     * @return
     */
    public ResponseEntity<Boolean> isReferralCodeExists(String referralCode){
        boolean isReferralCodeExists= referralRepository.existsByReferralCode(referralCode);
        return new ResponseEntity<>(isReferralCodeExists,HttpStatus.OK);
    }

    /**
     * @param referralDetailsRequestDto
     * @param result
     * @return
     */
    public ResponseEntity<String> addReferralDetails(ReferralDetailsRequestDto referralDetailsRequestDto, BindingResult result){
        if(result.hasErrors()){
            throw new ObjectNotFoundException("Request body passed is invalid","Invalid");
        }
        ReferralDetails referralDetails=ReferralDetails.builder()
                .referralUserEmail(referralDetailsRequestDto.getReferralUserEmail())
                .referralUserDiscount(referralDetailsRequestDto.getUserDiscount())
                .userEmail(referralDetailsRequestDto.getUserEmail())
                .userDiscount(referralDetailsRequestDto.getUserDiscount())
                .build();
        referralDetailsRepository.save(referralDetails);
        return new ResponseEntity<>("Referral details are successfully saved in the database",HttpStatus.OK);
    }
}



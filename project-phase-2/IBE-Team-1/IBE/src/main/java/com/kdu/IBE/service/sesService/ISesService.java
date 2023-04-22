package com.kdu.IBE.service.sesService;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;

import javax.mail.MessagingException;
import java.io.IOException;
@Service
public interface ISesService {
    void sesMessageSender(String sender, String recipient, String ratingsAndReviewsId,String bookingId) throws IOException;
    void sendRoomAvailabilityNotification(String sender, String recipient, String roomTypeId) throws IOException ;
    void sendOtp(String sender, String recipient, String otp) throws IOException ;
    void sendBookingEmail(String sender,String recipient,String image,String bookingId,String roomType,String startDate,String endDate);

    void send(SesClient client,
                     String sender,
                     String recipient,
                     String subject,
                     String bodyText,
                     String bodyHTML
    ) throws MessagingException, IOException ;
    }

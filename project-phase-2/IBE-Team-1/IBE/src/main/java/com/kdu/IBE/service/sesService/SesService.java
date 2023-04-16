package com.kdu.IBE.service.sesService;


import com.kdu.IBE.utils.SesServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

import java.io.IOException;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

import javax.mail.internet.MimeMessage;
@Slf4j
@Service
public class SesService {
    @Value("${aws.profile}")
    private String awsProfileName;
    @Value("${region}")
    private String region;
    @Autowired
    private SesServiceUtils sesServiceUtils;
    private SesClient client;
    private AwsRequestOverrideConfiguration myConf;

    /**
     * @param sender
     * @param recipient
     * @param ratingsAndReviewsId
     * @throws IOException
     */
    public void sesMessageSender(String sender, String recipient, String ratingsAndReviewsId) throws IOException {

        final String usage = sesServiceUtils.getUsage();
        String subject = "Please submit this review";
        Region region = Region.of(this.region);

        // The email body for non-HTML email clients.
        String bodyText = "Hello,\r\n" + "See the list below message. ";

        // The HTML body of the email.
        String bodyHTML = sesServiceUtils.getBodyHtml(ratingsAndReviewsId);
        this.client = SesClient.builder()
                .region(region)

//                .credentialsProvider(ProfileCredentialsProvider.create(this.awsProfileName))
                .build();
        try {
            send(client, sender, recipient, subject, bodyText, bodyHTML);
            client.close();
            log.info("Done");

        } catch (IOException | MessagingException e) {
            e.getStackTrace();
        }
    }


    public void sendOtp(String sender, String recipient, String otp) throws IOException {
        final String usage = sesServiceUtils.getUsage();
        String subject = "Please enter the otp";
        Region region = Region.of(this.region);

        // The email body for non-HTML email clients.
        String bodyText = "Hello,\r\n" + "See the list below message. ";

        // The HTML body of the email.
        String bodyHTML = sesServiceUtils.getOtpBodyHtml(otp);
        this.client = SesClient.builder()
                .region(region)
//                .credentialsProvider(ProfileCredentialsProvider.create(this.awsProfileName))
                .build();
        try {
            send(client, sender, recipient, subject, bodyText, bodyHTML);
            client.close();
            log.info("Done");

        } catch (IOException | MessagingException e) {
            e.getStackTrace();
        }
    }
//    public String getBookingEmail(String image,String bookingId,String roomType,String startDate,String endDate){

        public void sendBookingEmail(String sender,String recipient,String image,String bookingId,String roomType,String startDate,String endDate){
            final String usage = sesServiceUtils.getUsage();
            String subject = "Booking confirmation";
            Region region = Region.of(this.region);

            // The email body for non-HTML email clients.
            String bodyText = "Hello,\r\n" + "See the list below message. ";

            // The HTML body of the email.
            String bodyHTML = sesServiceUtils.getBookingEmail(image,bookingId,roomType,startDate,endDate);
            this.client = SesClient.builder()
                    .region(region)
//                    .credentialsProvider(ProfileCredentialsProvider.create(this.awsProfileName))
                    .build();
            try {
                send(client, sender, recipient, subject, bodyText, bodyHTML);
                client.close();
                log.info("Done");

            } catch (IOException | MessagingException e) {
                e.getStackTrace();
            }
        }

//    public void sendBookingConfirmationEmail(){
//
//    }
    /**
     * @param client
     * @param sender
     * @param recipient
     * @param subject
     * @param bodyText
     * @param bodyHTML
     * @throws MessagingException
     * @throws IOException
     */
    // snippet-start:[ses.java2.sendmessage.main]
    public void send(SesClient client,
                     String sender,
                     String recipient,
                     String subject,
                     String bodyText,
                     String bodyHTML
    ) throws MessagingException, IOException {

        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", 587);

        Session session = Session.getDefaultInstance(props);

        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(sender));
        msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipient));
        msg.setSubject(subject);
        msg.setContent(bodyHTML, "text/html");
        Transport transport = session.getTransport();

        try {
            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect("email-smtp.us-east-1.amazonaws.com", "AKIAXKKU4AB4UZ33UJPI", "BOP1XnFwPphpxfy6RF7TYdl2uTrTklJMHrskscWPpDd/");

            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
        } catch (Exception ex) {
            log.info("The email was not sent.");
            log.error("Error message: " + ex.getMessage());
        } finally {
            // Close and terminate the connection.
            transport.close();
        }
    }
}

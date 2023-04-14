package com.kdu.IBE.service.sesService;


import com.kdu.IBE.utils.ServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

import java.io.IOException;
import java.util.Properties;

import javax.mail.*;
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
    private ServiceUtils serviceUtils;
    private SesClient client;
    private AwsRequestOverrideConfiguration myConf;

    /**
     * @param sender
     * @param recipient
     * @param ratingsAndReviewsId
     * @throws IOException
     */
    public void sesMessageSender(String sender, String recipient, String ratingsAndReviewsId) throws IOException {

        final String usage =serviceUtils.getUsage();
        String subject = "Please submit this review";
        Region region = Region.of(this.region);

        // The email body for non-HTML email clients.
        String bodyText = "Hello,\r\n" + "See the list below message. ";

        // The HTML body of the email.
        String bodyHTML = serviceUtils.getBodyHtml(ratingsAndReviewsId);
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
        final String usage =serviceUtils.getUsage();
        String subject = "Please enter the otp";
        Region region = Region.of(this.region);

        // The email body for non-HTML email clients.
        String bodyText = "Hello,\r\n" + "See the list below message. ";

        // The HTML body of the email.
        String bodyHTML = serviceUtils.getOtpBodyHtml(otp);
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
            log.info("Attempting to send an email through Amazon SES " + "using the AWS SDK for Java...");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            message.writeTo(outputStream);
            ByteBuffer buf = ByteBuffer.wrap(outputStream.toByteArray());

            byte[] arr = new byte[buf.remaining()];
            buf.get(arr);

            SdkBytes data = SdkBytes.fromByteArray(arr);
            this.myConf = AwsRequestOverrideConfiguration.builder()
//                  .credentialsProvider(ProfileCredentialsProvider.create(this.awsProfileName))

                    .build();

            RawMessage rawMessage = RawMessage.builder()
                    .data(data)
                    .build();
            SendRawEmailRequest rawEmailRequest = SendRawEmailRequest.builder()
                    .rawMessage(rawMessage)
                    .overrideConfiguration(myConf)
                    .build();

            client.sendRawEmail(rawEmailRequest);
            log.info("Email message Sent");

        } catch (SesException e) {
            log.error(e.awsErrorDetails().errorMessage());
            System.exit(1);

        }
    }
}

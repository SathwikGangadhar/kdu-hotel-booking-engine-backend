package com.kdu.IBE.utils;

import org.springframework.stereotype.Component;

@Component
public class SesServiceUtils {
    public String getUsage(){
        return "\n" +
                "Usage:\n" +
                "    <sender> <recipient> <subject> \n\n" +
                "Where:\n" +
                "    sender - An email address that represents the sender. \n" +
                "    recipient -  An email address that represents the recipient. \n" +
                "    subject - The  subject line. \n";
    }
    public String getBodyHtml(String ratingsAndReviewsId){
        String url = "https://d7ohsf89hdgnd.cloudfront.net/feedback?id=" + ratingsAndReviewsId;

        return " <html>\n"+
                " <head></head>\n"+
                " <body>\n"+
                " <h1>Hello!</h1>\n"+
                " <p>Please click on this link to submit the review</p>\n"+
                " <p><a href=https://d7ohsf89hdgnd.cloudfront.net/feedback?id="+ratingsAndReviewsId+">https://d7ohsf89hdgnd.cloudfront.net/feedback?id="+ratingsAndReviewsId+"</a></p>\n"+
                " </body>\n"+
                "</html>";
    }
    public String getOtpBodyHtml(String otp){
        String otpMessage = "Your otp is"+otp;
        return " <html>\n"+
                " <head></head>\n"+
                " <body>\n"+
                " <h1>Hello!</h1>\n"+
                " <p>"+otpMessage+"</p>\n"+
                " </body>\n"+
                "</html>";
    }
    public String getBookingEmail(String image,String bookingId,String roomType,String startDate,String endDate){
        return "<!DOCTYPE html>" +
                "<html>\n" +
                "  <head>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    <title>Booking Confirmed</title>\n" +
                "    <style>\n" +
                "      /* Main styles */\n" +
                "      body {\n" +
                "        background-color: #f2f2f2;\n" +
                "        font-family: Arial, sans-serif;\n" +
                "        font-size: 16px;\n" +
                "        line-height: 1.5;\n" +
                "        color: #333;\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }\n" +
                "      p,\n" +
                "      h1 {\n" +
                "        margin: 0;\n" +
                "      }\n" +
                "\u200B\n" +
                "      /* Container */\n" +
                "      .container {\n" +
                "        max-width: 600px;\n" +
                "        margin: 0 auto;\n" +
                "        padding: 20px;\n" +
                "        background-color: #ffffff;\n" +
                "        border-radius: 10px;\n" +
                "        box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16);\n" +
                "        display: flex;\n" +
                "        flex-flow: column nowrap;\n" +
                "        background-color: #e5e9eb;\n" +
                "        gap: 0.5rem;\n" +
                "      }\n" +
                "\u200B\n" +
                "      /* Header */\n" +
                "      .header {\n" +
                "        text-align: center;\n" +
                "        margin-bottom: 20px;\n" +
                "      }\n" +
                "      .header h1 {\n" +
                "        margin-top: 0;\n" +
                "        font-size: 1.7rem;\n" +
                "        color: #26266d;\n" +
                "      }\n" +
                "      .image-container {\n" +
                "        border-radius: 5px;\n" +
                "      }\n" +
                "      .landing-page-image {\n" +
                "        width: 100%;\n" +
                "        box-shadow: 0px 4px 25px #bcbcbd;\n" +
                "        border-radius: 8px;\n" +
                "      }\n" +
                "\u200B\n" +
                "      /* Content */\n" +
                "      .content {\n" +
                "        margin-bottom: 20px;\n" +
                "        display: flex;\n" +
                "        flex-flow: column nowrap;\n" +
                "        gap: 0.5rem;\n" +
                "      }\n" +
                "      .content .user-booking-data{\n" +
                "       margin: 0.2rem auto;\n" +
                "        display: flex;\n" +
                "        flex-flow: column nowrap;\n" +
                "        gap:1rem;\n" +
                "        background-color: #9aa9b6;\n" +
                "        border: 2px solid  #7a8691;\n" +
                "        border-radius: 8px;\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "      .content .user-booking-data .type{\n" +
                "        display: flex;\n" +
                "        flex-flow: row nowrap;\n" +
                "        justify-content: space-between;\n" +
                "        gap:3rem;\n" +
                "        color: #ffffff;\n" +
                "        padding:0.2rem 0.5rem;\n" +
                "      }\n" +
                "      .visit-website {\n" +
                "        background-color: #26266d;\n" +
                "        padding: 0.5rem;\n" +
                "        margin: 0 auto;\n" +
                "        border-radius: 5px;\n" +
                "      }\n" +
                "      .visit-website .link {\n" +
                "        text-decoration: none;\n" +
                "        color: #ffffff;\n" +
                "      }\n" +
                "      /* Footer */\n" +
                "      .footer {\n" +
                "        text-align: center;\n" +
                "        color: #999;\n" +
                "        font-size: 14px;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div class=\"container\">\n" +
                "      <div class=\"header\">\n" +
                "        <h1 class=\"heading-name\">Thank You for Staying With Us!</h1>\n" +
                "      </div>\n" +
                "      <div class=\"image-container\">\n" +
                "        <img\n" +
                "          src=\""+image+"\"\n" +
                "          alt=\"Not Found!\"\n" +
                "          class=\"landing-page-image\"\n" +
                "        />\n" +
                "      </div>\n" +
                "      <div class=\"content\">\n" +
                "        <div class=\"info\">\n" +
                "          We are very happy that you have chosen us to serve you. We feel very\n" +
                "          happy to provide our services to you. We tried our best in order to\n" +
                "          make you feel comfortable like home. We hope that you will be\n" +
                "          satisfied with our services, and choose us for the next time also.\n" +
                "        </div>\n" +
                "        <div class=\"user-booking-data\">\n" +
                "          <div class=\"type\">\n" +
                "            <p class=\"type-name\">Booking ID</p>\n" +
                "            <p class=\"type-value\">#"+bookingId+"</p>\n" +
                "          </div>\n" +
                "          <div class=\"type\">\n" +
                "            <p class=\"type-name\">Room Type</p>\n" +
                "            <p class=\"type-value\">"+roomType+"</p>\n" +
                "          </div>\n" +
                "          <div class=\"type\">\n" +
                "            <p class=\"type-name\">Check In Date</p>\n" +
                "            <p class=\"type-value\">"+startDate+"</p>\n" +
                "          </div>\n" +
                "          <div class=\"type\">\n" +
                "            <p class=\"type-name\">Check Out Date</p>\n" +
                "            <p class=\"type-value\">"+endDate+"</p>\n" +
                "          </div>\n" +
                "        </div>\n" +
                "        <button class=\"visit-website\">\n" +
                "          <a\n" +
                "            href=\"https://d7ohsf89hdgnd.cloudfront.net\"\n" +
                "            target=\"_blank\"\n" +
                "            class=\"link\"\n" +
                "            >CLICK HERE</a\n" +
                "          >\n" +
                "        </button>\n" +
                "        <p>Best regards,</p>\n" +
                "        <p>The Team 1</p>\n" +
                "      </div>\n" +
                "      <div class=\"footer\">\n" +
                "        <p>You received this email because you subscribed to our newsletter.</p>\n" +
                "        <p>Team 1 &bull; #570/571, Kickdrum. &bull; Bengaluru, India 560098</p>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
    }
}

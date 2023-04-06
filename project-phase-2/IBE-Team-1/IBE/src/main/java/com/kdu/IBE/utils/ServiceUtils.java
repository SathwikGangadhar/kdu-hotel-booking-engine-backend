package com.kdu.IBE.utils;

import org.springframework.stereotype.Component;

@Component
public class ServiceUtils {
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
        return " <html>\n"+
                " <head></head>\n"+
                " <body>\n"+
                " <h1>Hello!</h1>\n"+
                " <p>Please click on this link to submit the review</p>\n"+
                " <p><a href=http://localhost:3000/?id="+ratingsAndReviewsId+">click here</a></p>\n"+
                " </body>\n"+
                "</html>";
    }
}

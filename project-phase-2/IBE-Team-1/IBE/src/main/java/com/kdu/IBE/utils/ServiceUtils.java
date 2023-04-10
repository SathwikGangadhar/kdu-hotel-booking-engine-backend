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
}

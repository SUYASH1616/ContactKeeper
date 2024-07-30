package com.scm.Services;

public interface EmailService {
    void sendMail(String to,String subject,String body);

    void sendEmailWithAttachment();
    void sendEmailWithHtml();
}

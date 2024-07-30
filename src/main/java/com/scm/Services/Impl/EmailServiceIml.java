package com.scm.Services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.scm.Services.EmailService;

@Service
public class EmailServiceIml implements EmailService {

    @Autowired
    private JavaMailSender eMailSender;
    @Override
    public void sendEmailWithAttachment() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void sendEmailWithHtml() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void sendMail(String to, String subject, String body) {
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        simpleMailMessage.setFrom("scm@demomailtrap.com");
        eMailSender.send(simpleMailMessage);
        
    }
    
}

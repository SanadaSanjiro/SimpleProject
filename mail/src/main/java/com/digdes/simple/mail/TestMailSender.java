package com.digdes.simple.mail;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class TestMailSender {

    private final JavaMailSender emailSender;

    public TestMailSender(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void send(String text) {
        MailMessageModel mmm = JSONObject.parseObject(text, MailMessageModel.class);
        System.out.println(mmm);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("digdestaskmanager@gmail.com");
        message.setTo(mmm.getTo());
        message.setSubject(mmm.getSubject());
        message.setText(mmm.getText());
        emailSender.send(message);
    }
}

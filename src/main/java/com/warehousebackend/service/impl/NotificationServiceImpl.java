package com.warehousebackend.service.impl;


import com.warehousebackend.model.entities.UserEntity;
import com.warehousebackend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final JavaMailSender javaMailSender;

    @Autowired
    public NotificationServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendNotification(UserEntity userEntity) {
//        SimpleMailMessage mail = new SimpleMailMessage();
////        String mailBody = "http://localhost:3000/password/" + userEntity.getId();
//
//        String mailBody = "<a href='http://localhost:3000/password/" + userEntity.getId() + "'>Click here to reset your password</a>";
//
//
//        mail.setTo(userEntity.getEmail());
//        mail.setFrom("ciradukunda2024@gmail.com");
//        mail.setSubject("Change your password");
//        mail.setText(mailBody);
//
//        javaMailSender.send(mail);


        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(userEntity.getEmail());
            helper.setSubject("Change your password");

            String mailBody = "<a href='http://localhost:3000/password/" + userEntity.getId() + "'>Click here to reset your password</a>";
            helper.setText(mailBody, true);

            javaMailSender.send(message);
        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }
}

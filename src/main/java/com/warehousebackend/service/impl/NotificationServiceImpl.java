package com.warehousebackend.service.impl;


import com.warehousebackend.model.entities.UserEntity;
import com.warehousebackend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final JavaMailSender javaMailSender;

    @Autowired
    public NotificationServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendNotification(UserEntity userEntity) {
        SimpleMailMessage mail = new SimpleMailMessage();
        String mailBody = "http://localhost:3000/password/" + userEntity.getId();
        mail.setTo(userEntity.getEmail());
        mail.setFrom("tresorxavier16@gmail.com");
        mail.setSubject("Change your password");
        mail.setText("Click the link to reset your password: " + mailBody);

        javaMailSender.send(mail);
    }
}
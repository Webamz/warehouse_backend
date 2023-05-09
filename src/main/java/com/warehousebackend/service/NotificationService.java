package com.warehousebackend.service;


import com.warehousebackend.model.entities.UserEntity;
import org.springframework.scheduling.annotation.Async;

public interface NotificationService {
    @Async
    void sendNotification(UserEntity userEntity);
}

package com.example.personnel_backend.client;

import com.example.personnel_backend.dto.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-backend", url = "http://localhost:8081")
public interface NotificationClient {

    @PostMapping("/api/notifications")
    ResponseEntity <Void> sendNotification(@RequestBody NotificationRequest notificationRequest);

}


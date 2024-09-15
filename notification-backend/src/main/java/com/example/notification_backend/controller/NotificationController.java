package com.example.notification_backend.controller;

import com.example.notification_backend.dto.NotificationRequest;
import com.example.notification_backend.service.NotificationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/notifications")
@Validated
@Tag(name = "Notifications", description = "Notification management API")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Operation(summary = "Send Notification", description = "Sends a notification")
    @PostMapping
    public ResponseEntity<Void> sendNotification(@RequestBody NotificationRequest notificationRequest) {
        notificationService.sendNotification(notificationRequest);
        return ResponseEntity.ok().build();
    }
}



package com.example.notification_backend.service;

import com.example.notification_backend.dto.NotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Value("${spring.mail.username}")
    private String from;
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final JavaMailSender mailSender;
    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendNotification(NotificationRequest notificationRequest) {
        if (notificationRequest.getRecipient() == null || notificationRequest.getRecipient().isEmpty()) {
            logger.error("Recipient address is null or empty");
            throw new IllegalArgumentException("To address must not be null or empty");
        }
        logger.info("Sending notification to {}: {}", notificationRequest.getRecipient(), notificationRequest.getMessage());
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);
            messageHelper.setFrom(from);
            messageHelper.setTo(notificationRequest.getRecipient());
            messageHelper.setSubject(notificationRequest.getSubject());
            messageHelper.setText(notificationRequest.getMessage(), notificationRequest.isHtml());

            mailSender.send(messageHelper.getMimeMessage());
            logger.info("Notification sent successfully to {}", notificationRequest.getRecipient());
        } catch (Exception e) {
            logger.error("Failed to send notification", e);
        }
    }
}



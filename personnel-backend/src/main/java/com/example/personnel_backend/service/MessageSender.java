package com.example.personnel_backend.service;

import com.example.personnel_backend.dto.NotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {

    private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);

    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;

    public MessageSender(RabbitTemplate rabbitTemplate, @Value("${spring.rabbitmq.template.exchange}")
    String exchangeName) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
    }

    public void sendMessage(NotificationRequest notificationRequest) {
        try {
            rabbitTemplate.convertAndSend(exchangeName, "notificationRoutingKey", notificationRequest);
            logger.info("Sent notification request: {}", notificationRequest);
        } catch (Exception e) {
            logger.error("Failed to send notification request", e);
        }
    }
}



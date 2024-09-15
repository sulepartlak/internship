package com.example.notification_backend.service;

import com.example.notification_backend.dto.NotificationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationProducer {

    private static final Logger logger = LoggerFactory.getLogger(NotificationProducer.class);

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public NotificationProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendNotification(NotificationRequest notificationRequest) {
        if (notificationRequest == null) {
            logger.error("Notification request is null");
            throw new IllegalArgumentException("Notification request cannot be null");
        }

        try {
            // Serialize NotificationRequest to JSON
            String jsonPayload = objectMapper.writeValueAsString(notificationRequest);

            // Set message properties
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);

            // Create a Message with JSON payload and properties
            Message message = new Message(jsonPayload.getBytes(), messageProperties);

            // Send the message to the specified RabbitMQ exchange and routing key
            rabbitTemplate.send("notificationQueue", message);

            logger.info("Notification sent to RabbitMQ: {}", notificationRequest);
        } catch (JsonProcessingException e) {
            logger.error("Error converting notification request to JSON", e);
        } catch (Exception e) {
            logger.error("Failed to send notification to RabbitMQ", e);
        }
    }
}






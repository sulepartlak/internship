package com.example.personnel_backend.service;

import com.example.personnel_backend.client.NotificationClient;
import com.example.personnel_backend.entity.Personnel;
import com.example.personnel_backend.repository.PersonnelRepository;
import com.example.personnel_backend.dto.NotificationRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class PersonnelService {

    private static final Logger logger = LoggerFactory.getLogger(PersonnelService.class);

    private final PersonnelRepository personnelRepository;
    private final NotificationClient notificationClient;

    @Autowired
    public PersonnelService(PersonnelRepository personnelRepository, NotificationClient notificationClient) {
        this.personnelRepository = personnelRepository;
        this.notificationClient = notificationClient;
    }

    public List<Personnel> getAllPersonnel() {
        logger.info("Fetching all personnel records");
        return personnelRepository.findAll();
    }

    public Optional<Personnel> getPersonnelById(Long id) {
        logger.info("Fetching Personnel with ID: {}", id);
        return personnelRepository.findById(id);
    }

    public Personnel savePersonnel(@Valid Personnel personnel) {
        Personnel savedPersonnel;

        if (personnel.getId() == null) {
            // Creating new personnel
            savedPersonnel = personnelRepository.save(personnel);
            logger.info("Created new personnel with ID: {}", savedPersonnel.getId());

            // Send notification when new personnel is created
            sendNotificationForNewPersonnel(savedPersonnel);

        } else {
            // Current personnel is being updated
            if (!personnelRepository.existsById(personnel.getId())) {
                logger.error("Personnel with ID {} does not exist", personnel.getId());
                throw new IllegalArgumentException("Personnel with ID " + personnel.getId() + " does not exist");
            }
            savedPersonnel = personnelRepository.save(personnel);
            logger.info("Updated personnel with ID: {}", savedPersonnel.getId());
        }

        return savedPersonnel;
    }

    private void sendNotificationForNewPersonnel(Personnel personnel) {
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setRecipient("dersicin67@hotmail.com");
        notificationRequest.setSubject("New Personnel Created");
        notificationRequest.setMessage("New personnel created: " + personnel.getFirstName()
                + " " + personnel.getLastName() + " with position " + personnel.getPosition());
        notificationRequest.setHtml(false);

        try {
            notificationClient.sendNotification(notificationRequest);
            logger.info("Notification sent for new personnel creation to {}", notificationRequest.getRecipient());
        } catch (Exception e) {
            logger.error("Failed to send notification", e);
        }
    }

    public void deletePersonnel(Long id) {
        logger.info("Deleting Personnel with ID: {}", id);
        personnelRepository.deleteById(id);
    }
}

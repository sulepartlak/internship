package com.example.personnel_backend.dto;

import java.util.Objects;

public class NotificationRequest {
    private String recipient;   // Recipient email address
    private String subject;     // Email subject
    private String message;     // Email content
    private boolean isHtml;     // HTML content support
    private String personnelId; // Personnel ID to identify the related personnel

    // Parameterized constructor
    public NotificationRequest(String recipient, String subject, String message, boolean isHtml, String personnelId) {
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
        this.isHtml = isHtml;
        this.personnelId = personnelId;
    }

    // Default constructor
    public NotificationRequest() {
    }

    // Getters and Setters
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isHtml() {
        return isHtml;
    }

    public void setHtml(boolean isHtml) {
        this.isHtml = isHtml;
    }
    public String getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(String personnelId) {
        this.personnelId = personnelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationRequest that = (NotificationRequest) o;
        return isHtml == that.isHtml &&
                Objects.equals(recipient, that.recipient) &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(message, that.message) &&
                Objects.equals(personnelId, that.personnelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipient, subject, message, isHtml, personnelId);
    }

    @Override
    public String toString() {
        return "NotificationRequest{" +
                "recipient='" + recipient + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", isHtml=" + isHtml +
                ", personnelId='" + personnelId + '\'' +
                '}';
    }
}

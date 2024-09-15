package com.example.notification_backend.dto;

import java.util.Objects;

public class NotificationRequest {
    private String recipient;
    private String subject;
    private String message;
    private boolean isHtml;

    public NotificationRequest(String recipient, String subject, String message, boolean isHtml) {
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
        this.isHtml = isHtml;
    }
    public NotificationRequest() {
    }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationRequest that = (NotificationRequest) o;
        return isHtml == that.isHtml &&
                Objects.equals(recipient, that.recipient) &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipient, subject, message, isHtml);
    }

    @Override
    public String toString() {
        return "NotificationRequest{" +
                "recipient='" + recipient + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", isHtml=" + isHtml +
                '}';
    }
}

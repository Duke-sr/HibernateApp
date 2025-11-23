package com.duke.notification.service;

public interface MailService {
    void sendEmail(String email, String subject, String text);
}
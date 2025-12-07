package com.duke.notification.service;

import com.duke.common.dto.KafkaDto;

public interface NotificationService {
    void process(KafkaDto message);
}
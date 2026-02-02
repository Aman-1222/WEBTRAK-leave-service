package com.company.leave_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeaveNotificationProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "leave-notification";

    public void send(String message) {
        kafkaTemplate.send(TOPIC, message);
        System.out.println(" Kafka Message Sent: " + message);
    }
}

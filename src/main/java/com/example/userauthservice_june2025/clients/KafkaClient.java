package com.example.userauthservice_june2025.clients;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaClient {

    // These will have two argument
    // 1. Topic (string)
    // 2. Message (Json in form of string)
    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaClient(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic,String message){
        kafkaTemplate.send(topic, message);
    }

}

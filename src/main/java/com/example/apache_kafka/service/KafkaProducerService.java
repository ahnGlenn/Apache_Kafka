package com.example.apache_kafka.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
/*
* 데이터를 Kafka 주제로 전송하는 프로듀서를 구현
*/
@Service
public class KafkaProducerService {

    private static final String TOPIC_NAME = "topicNo1";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        kafkaTemplate.send(TOPIC_NAME, message);
        System.out.println("Sent message: " + message);
    }
}
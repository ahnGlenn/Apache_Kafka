package com.example.apache_kafka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


/*
* 데이터를 Kafka 주제로 전송하는 프로듀서를 구현
*/
@Service
public class KafkaProducerService {

    private static final String TOPIC = "my-topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        kafkaTemplate.send(TOPIC, message);
        System.out.println("Produced message: " + message);
    }
}
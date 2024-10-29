package com.example.apache_kafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
* Kafka에서 데이터를 실시간으로 소비할 컨슈머를 구현
*/
@Service
public class KafkaConsumerService {

    private final List<String> messages = new ArrayList<>();

    @KafkaListener(topics = "topicNo1", groupId = "my-group")
    public void consumeMessage(String message) {
        System.out.println("Consumed message: " + message);
        messages.add(message);
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }
}
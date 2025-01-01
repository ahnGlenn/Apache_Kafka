package com.example.apache_kafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/*
* Kafka에서 데이터를 실시간으로 소비할 컨슈머를 구현
*/
@Service
public class KafkaConsumerService {
    // 메시지를 카운트하기 위함.
    private static final AtomicInteger receivedCounter = new AtomicInteger(0);

    @KafkaListener(topics = "topicNo1", groupId = "my-group")
    public void consumeMessage(String message) {
        int receivedMessages = receivedCounter.incrementAndGet();
        System.out.println("Total Received Messages: " + receivedMessages);
    }

    // 1초 동안 수신된 메시지 수를 측정:
    @Scheduled(fixedRate = 1000)
    public void logConsumerTPS() {
        System.out.println("Consumer TPS: " + receivedCounter.getAndSet(0));
    }
}
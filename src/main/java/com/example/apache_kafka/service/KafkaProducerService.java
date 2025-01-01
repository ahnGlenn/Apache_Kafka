package com.example.apache_kafka.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

    // 메시지를 카운트 하기 위함.
    private static final AtomicInteger messageCounter = new AtomicInteger(0);

    /*
    * 1. 일반 비동기 방식을 사용할 경우
    */
    public void sendMessage(String message) {
        kafkaTemplate.send(TOPIC_NAME, message);
        int sentMessages = messageCounter.incrementAndGet();
        System.out.println("Total Sent Messages: " + sentMessages);
    }


    /*
    * 2. 배치 방식을 사용할 경우
    */
    public void sendBatchMessages(List<String> messages) {
        messages.forEach(message -> kafkaTemplate.send(TOPIC_NAME, message));
    }

    // 1초 동안 전송된 메시지 수를 측정:
    @Scheduled(fixedRate = 1000)
    public void logProducerTPS() {
        System.out.println("Producer TPS: " + messageCounter.getAndSet(0));
    }
}
package com.example.apache_kafka.controller;

import com.example.apache_kafka.service.KafkaConsumerService;
import com.example.apache_kafka.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private KafkaConsumerService kafkaConsumerService;

    // 메시지 전송 엔드포인트
    @GetMapping("/produce")
    public String produceMessage(@RequestParam("message") String message) {
        kafkaProducerService.sendMessage(message);
        return "Message sent: " + message;
    }

    // 메시지 수신 엔드포인트
    @GetMapping("/consume")
    public List<String> consumeMessages() {
        return kafkaConsumerService.getMessages();
    }

}
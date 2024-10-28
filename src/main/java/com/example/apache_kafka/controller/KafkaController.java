package com.example.apache_kafka.controller;

import com.example.apache_kafka.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @PostMapping("/publish")
    public String publishMessage(@RequestParam("message") String message) {
        kafkaProducerService.sendMessage(message);
        return "Message published successfully";
    }
}
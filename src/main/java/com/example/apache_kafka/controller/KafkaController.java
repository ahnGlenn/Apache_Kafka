package com.example.apache_kafka.controller;

import com.example.apache_kafka.service.KafkaProducerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kafka")
public class KafkaController {
    private final KafkaProducerService kafkaproducerService;

    public KafkaController(KafkaProducerService kafkaproducerService) {
        this.kafkaproducerService = kafkaproducerService;
    }

    // 메시지 전송 엔드포인트
    @GetMapping("/send")
    public String sendMessage(@RequestParam String message) {
        kafkaproducerService.sendMessage(message);
        return "Message sent: " + message;
    }

//    // 메시지 전송 엔드포인트
//    @GetMapping("/produce")
//    public String produceMessage(@RequestParam("message") String message) {
//        kafkaProducerService.sendMessage(message);
//        return "Message sent: " + message;
//    }
//
//    // 메시지 수신 엔드포인트
//    @GetMapping("/consume")
//    public List<String> consumeMessages() {
//        return kafkaConsumerService.getMessages();
//    }

}
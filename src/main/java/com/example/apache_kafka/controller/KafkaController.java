package com.example.apache_kafka.controller;

import com.example.apache_kafka.service.KafkaProducerService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/kafka")
public class KafkaController {
    private final KafkaProducerService kafkaProducerService;

    public KafkaController(KafkaProducerService kafkaproducerService) {
        this.kafkaProducerService = kafkaproducerService;
    }

    // 단일 메시지 전송 http://localhost:8080/kafka/send?message=HelloKafka
//    @GetMapping("/send")
//    public String sendMessage(@RequestParam String message) {
//        kafkaProducerService.sendMessage(message);
//        return "Message sent: " + message;
//    }


    /*
    * 비동기 메시지 전송
    * 설명: 메서드 호출이 즉시 반환되고, 실제 작업은 별도의 스레드에서 비동기적으로 실행(메시지를 병렬로 전송하여 처리 속도 올림)
    */
    @Async("kafkaTaskExecutor")
    public void sendAsyncMessage(String message) {
        kafkaProducerService.sendMessage(message);
    }


    // 1. 스케줄러 + 비동기 + 멀티쓰레드 환경으로 메시지 발행
    //    스케줄러메시지 발생 > 비동기(kafkaTaskExecutor 쓰레드참조) > 프로듀서로 메시지 발행
    private boolean hasRun = false; // 최초 한번 실행을 확인하기위해
    @Scheduled(fixedRate = 1000) //1초마다 메시지 1000개 전송
    public void sendMessages() {
        if (hasRun) return; // 이미 실행되었다면 종료
        hasRun = true;

        for (int i = 0; i < 2000; i++) {
            String message = "Message " + i + " at " + System.currentTimeMillis();
            sendAsyncMessage(message);
        }
    }


    // 2. 카프카 내장 배치환경으로 메시지 발행할 경우 사용(동기이므로, 어디선가 콜 해야함)
    // @Scheduled(fixedRate = 1000)
    public void sendBatchMessages() {
        List<String> messages = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            messages.add("Message " + i + " at " + System.currentTimeMillis());
        }
        kafkaProducerService.sendBatchMessages(messages);
    }
}
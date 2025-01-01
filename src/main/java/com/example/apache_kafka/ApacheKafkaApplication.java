package com.example.apache_kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


// 처음 프젝 생성 후 디비연동없이 사용시 아래 추가.
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableScheduling // 스케줄링 활성화
@EnableAsync      // 비동기 처리 활성화
public class ApacheKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApacheKafkaApplication.class, args);
    }

}

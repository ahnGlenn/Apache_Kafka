package com.example.apache_kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


// 처음 프젝 생성 후 디비연동없이 사용시 아래 추가.
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class ApacheKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApacheKafkaApplication.class, args);
    }

}

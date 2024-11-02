<!-- 
- 카프카 :  https://resilient-923.tistory.com/402 
- 카프카 명령어(프로듀서 메시지 전송 후 컨슈머 메시지 get, 그룹생성, 토픽생성 등) : https://velog.io/@denver_almighty/Kafka-Topic-%EB%A7%8C%EB%93%A4%EA%B8%B0
-->
# Apache_Kafka
- Kafka를 통한 대용량 데이터 교류
- 데이터 프로듀서(생성자)가 Kafka를 통해 데이터를 보내고, 데이터 컨슈머(소비자)가 데이터를 실시간으로 처리하는 구조

<br/>

# Why? Kafka vs RabbitMq
- ref : https://aws.amazon.com/ko/compare/the-difference-between-rabbitmq-and-kafka/
- 100만 건 이상의 데이터를 실시간 전송하고, 다른 애플리케이션에서 데이터 처리를 수행해야 한다면 Kafka를 사용하는 것이 더 적합

<br/>

# 1. 프로젝트 목표
- 도커로 zookeepr, kafka환경 구축
- 데이터를 Kafka를 통한 실시간 데이터 교류
- Kafka 메시지를 팀원이 구독하여 특정 DB까지 저장하도록
  
<br/>

# 2. 프로젝트 아키텍처
<img width="663" alt="Screenshot 2024-11-02 at 3 47 18 PM" src="https://github.com/user-attachments/assets/9e10f734-9905-4f03-9fc8-1d467fe6bf32">



<br/>

# 3. 구현
(docker 미사용 시) - Zookeeper 설치 및 실행(kafka borker를 위한 선실행 필수!)
1. SpringBoot 프로젝트 생성
2. SpringBoot > docker-compose.yml (zookeeper, kafka) 실행

<br/>

# 4. 테스트 케이스
1. Kafka 실행:
   - Kafka 서버와 ZooKeeper가 로컬에서 실행 시킨다
2. 애플리케이션 실행:
   - 스프링부트 실행
3. 테스트
   - REST API 클라이언트(Postman, Jmeter 등)를 사용하여 http://localhost:8080/publish?message=Hello Kafka로 요청을 보내면, 메시지가 Kafka에 전송된다.
   - 컨슈머 서비스에 메시지가 콘솔에 출력되는지 확인한다.
<br/>

# 5. 성능 최적화 및 측정 계획
1. Kafka 설정 최적화:
   - 파티션 수 및 복제 인수 설정을 최적화하여, 병렬 처리를 극대화.
   - 적절한 Acknowledgement 설정 (acks) 및 배치 처리 옵션 (batch.size)을 조정

2. 데이터 처리 로직 확장:
   - 컨슈머에서 받은 데이터를 실시간으로 분석하거나, 데이터베이스에 저장하는 등의 추가 처리를 구현.
   - 비동기 처리를 위해 @Async 어노테이션을 활용하거나, ExecutorService를 사용.

3. 모니터링 및 로깅:
   - Kafka 스트림 모니터링 도구(Kafka Manager, Confluent Control Center 등)를 사용하여 메시지 전송 상태를 실시간으로 확인.
   - Spring Boot Actuator를 이용해 애플리케이션 상태를 모니터링.

3. 클라우드 배포 :
   - Docker를 이용해 Kafka와 Spring Boot 애플리케이션을 클라우드에 배포하고 확장.

<br/><br/>

# 6. 프로듀서 메시지 생성 > 컨슈머 메시지 수신 및 확인 (linux 환경)
> 프로듀서 시작

  - 프로듀서 시작 및 입력모드 실행.
  - 입력모드에 메시지 작성하면 topicNo1에 저장
```
bin/kafka-console-producer.sh --topic topicNo1 --bootstrap-server localhost:9092
```
> 컨슈머 시작

  - Kafka 컨슈머는 토픽으로부터 데이터를 읽어오는 역할
  - 다음 명령어로 컨슈머를 시작하여 프로듀서가 topicNo1으로 보낸 메시지를 확인
```
bin/kafka-console-consumer.sh --topic topicNo1 --bootstrap-server localhost:9092 --from-beginning
```




<br/><br/>

# 7. 카프카 재기동
1. SpringBoot앱 종료
2. kafka 종료
   - bin/kafka-server-stop.sh
   - sudo rm -rf /tmp/kafka-logs (자동생성 로그 삭제)
   - ps -ef | grep kafka
   - 9092, already in use 발생 시 > kill -9 [pid]
3. Zookeeper 종료
   - ./zkServer.sh (start, stop, status)
4. Zookeeper 재시작
   - ./zkServer.sh (start, stop, status)
5. Kafka 시작
   - bin/kafka-server-start.sh config/server.properties
6. SpringBoot앱 재시작
7. topic 생성/확인
   - bin/kafka-topics.sh --create --topic <topic_name> --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1
   - 위에 <topic_name> 에서 괄호는 제거 해야함.
   - ./bin/kafka-topics.sh --bootstrap-server localhost:9092 --list
<br/>


<!-- 
css

Apache_kafka/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── apache_kafka/
│   │   │               ├── controller/
│   │   │               │   └── KafkaController.java
│   │   │               ├── service/
│   │   │               │   ├── KafkaProducerService.java
│   │   │               │   └── KafkaConsumerService.java
│   │   │               └── MyKafkaProjectApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── logback-spring.xml (선택 사항: 로깅 설정 파일)
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── mykafkaproject/
│                       └── MyKafkaProjectApplicationTests.java
├── build.gradle
└── settings.gradle

-->

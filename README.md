<!-- 
- 카프카 :  https://resilient-923.tistory.com/402 
- 카프카 명령어(프로듀서 메시지 전송 후 컨슈머 메시지 get, 그룹생성, 토픽생성 등) : https://velog.io/@denver_almighty/Kafka-Topic-%EB%A7%8C%EB%93%A4%EA%B8%B0
- 대용량 데이터를 주고받을때,  카프카를 쓰는게 맞는가?
-->
# Apache_Kafka
Kafka 기반 대용량 데이터 처리와 장애 복구 실험
실시간 처리 및 보안 설정을 통한 완벽한 메시징 시스템 구축

<br/>

# Why? Kafka vs RabbitMq
- ref : https://aws.amazon.com/ko/compare/the-difference-between-rabbitmq-and-kafka/
- 100만 건 이상의 데이터를 실시간 전송하고, 다른 애플리케이션에서 데이터 처리를 수행해야 한다면 Kafka를 사용하는 것이 더 적합

<br/>

# 1. 프로젝트 목표
<pre>
1️⃣ 대용량 데이터 처리 → ✅ 
2️⃣ 장애 복구와 데이터 복제 → ✅
3️⃣ 이벤트 정렬 및 순서 보장 → 
4️⃣ 장애애 복원력 및 재처리 →
5️⃣ 멀티 클러스터 데이터 복제 →
6️⃣ Kafka Streams와 실시간 처리 →
</pre>
  
<br/>

# 2. 프로젝트 아키텍처
<img width="663" alt="Screenshot 2024-11-02 at 3 47 18 PM" src="https://github.com/user-attachments/assets/9e10f734-9905-4f03-9fc8-1d467fe6bf32">

<br/>

# 3. 구현
(docker 미사용 시) - Zookeeper 설치 및 실행(kafka borker를 위한 선실행 필수!)
1. SpringBoot 프로젝트 생성
2. SpringBoot > docker-compose.yml (zookeeper, kafka) 실행
3. 


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
    mac (docker)
    - 도커 프로세스 확인 : docker ps
    - 도커 프로세스 다운/재기동 : docker-compose down/ docker-compose up -d
    - docker cli 접속/나가기 : docker exec -it kafka /bin/bash   /    exit
    - topic 생성 : kafka-topics --create --topic topicNo1 --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
    - topic 삭제 : kafka-topics --delete --topic topicNo1 --bootstrap-server localhost:9092
    - topic 확인 :
      1. cli 접속 : docker exec -it apache_kafka-kafka-1-1 /bin/bash
      2. kafka-topics --bootstrap-server kafka-1:29091 --list
      3. 해당 토픽내 메시지 확인 : kafka-console-consumer --bootstrap-server kafka-1:29091 --topic topicNo1 --from-beginning
    메시지 발송/확인
    - cli접속 후 메시지 발행 : kafka-console-producer --topic topicNo1 --bootstrap-server localhost:9092
    - 메시지 확인 : kafka-console-consumer --topic topicNo1 --from-beginning --bootstrap-server localhost:9092
    
    zookeeper cli 접속법
    - docker exec -it zookeeper /bin/bash
    - 일단 찾아봐 : find / -name zkCli.sh 2>/dev/null
    - 없으면 cd /usr/bin 에서 zookeeper-shell 실행시켜 > zookeeper-shell localhost:2181
    - ls / : 주키퍼 노드 확인, ls /brokers/ids
    - 문제되는 ids 삭제 delete /brokers/ids/2


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

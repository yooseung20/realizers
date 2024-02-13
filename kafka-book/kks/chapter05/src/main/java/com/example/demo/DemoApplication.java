package com.example.demo;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String bootstrapServers = "localhost:9091";
		Properties props = new Properties();
		props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true"); // 정확히 한번 전송을 위한 설정
		props.setProperty(ProducerConfig.ACKS_CONFIG, "all"); // 정확히 한번 전송을 위한 설정
		props.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5"); // 정확히 한번 전송을 위한 설정
		props.setProperty(ProducerConfig.RETRIES_CONFIG, "5"); // 정확히 한번 전송을 위한 설정
		props.setProperty(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "peter-transaction-01"); // 정확히 한번 전송을 위한 설정

		Producer<String, String> producer = new KafkaProducer<>(props);

		producer.initTransactions(); // 프로듀서 트랜잭션 초기화
		producer.beginTransaction(); // 프로듀서 트랜잭션 시작
		try {
			for (int i = 0; i < 1; i++) {
				ProducerRecord<String, String> record = new ProducerRecord<>("peter-test-05",
					"Apache Kafka is a distributed streaming platform - " + i);
				producer.send(record);
				producer.flush();
				System.out.println("Message sent successfully");
			}
		} catch (Exception e) {
			producer.abortTransaction(); // 프로듀서 트랜잭션 중단
			e.printStackTrace();
		} finally {
			producer.commitTransaction(); // 프로듀서 트랜잭션 커밋
			producer.close();
		}
	}
}

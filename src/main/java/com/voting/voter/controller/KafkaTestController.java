package com.voting.voter.controller;

import java.util.Map;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class KafkaTestController {

    @GetMapping("/kafka-test")
    public String testKafka() {
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(Map.of(
                "bootstrap.servers", "localhost:9092",
                "key.serializer", "org.apache.kafka.common.serialization.StringSerializer",
                "value.serializer", "org.apache.kafka.common.serialization.StringSerializer"))) {
            producer.send(new ProducerRecord<>("user-created", "key", "test message"));
            return "Kafka reachable!";
        } catch (Exception e) {
            return "Kafka NOT reachable: " + e.getMessage();
        }
    }
}

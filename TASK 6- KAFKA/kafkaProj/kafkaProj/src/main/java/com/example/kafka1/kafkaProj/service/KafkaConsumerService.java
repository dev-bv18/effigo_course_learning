package com.example.kafka1.kafkaProj.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class); // Logger instance
//
//    // Listen to a specific topic or use regex to match topics
//    @KafkaListener(topics = "my-topic", groupId = "consumer-group-1")
//    public void consume(ConsumerRecord<String, String> record) {
//        try {
//            // Print out the consumed message with topic and value
//            logger.info("Consumed message from topic: {}, Message: {}", record.topic(), record.value());
//        } catch (Exception e) {
//            // Handle any exceptions that occur while consuming the message
//            logger.error("Error while consuming message from topic: {}, Message: {}", record.topic(), record.value(), e);
//        }
//    }

    // Optional: If you want to listen to all topics, uncomment the below method and comment the specific topic listener
    /*
    @KafkaListener(topicPattern = ".*", groupId = "consumer-group-1")
    public void consumeAllTopics(ConsumerRecord<String, String> record) {
        try {
            logger.info("Consumed message from topic: {}, Message: {}", record.topic(), record.value());
        } catch (Exception e) {
            logger.error("Error while consuming message from topic: {}, Message: {}", record.topic(), record.value(), e);
        }
    }
    */
}

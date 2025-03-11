package com.example.kafka1.kafkaProj.service;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.TimeoutException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.kafka.KafkaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

@Service
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // Method to send message with fallback mechanism
    public boolean sendMessageWithFallback(String topic, String message) {
        int maxRetries = 3;
        int retryCount = 0;

        while (retryCount < maxRetries) {
            try {
                // Attempt to send the message
                kafkaTemplate.send(new ProducerRecord<>(topic, message)).get(10, TimeUnit.SECONDS); // Blocking until sent
                logger.info("Message successfully sent to topic '{}': {}", topic, message);
                return true;
            } catch (TimeoutException | KafkaException e) {
                retryCount++;
                logger.warn("Failed to send message to topic '{}', attempt {}/{}. Retrying...", topic, retryCount, maxRetries);
            } catch (Exception e) {
                logger.error("Unexpected error while sending message to topic '{}': {}", topic, e.getMessage(), e);
                return false; // Failure in non-retryable cases
            }
        }

        logger.error("Failed to send message to topic '{}' after {} attempts.", topic, maxRetries);
        return false; // Fallback: message couldn't be sent
    }

    // Normal sendMessage (without fallback)
    public void sendMessage(String topic, String message) {
        try {
            kafkaTemplate.send(topic, message);
            logger.info("Message successfully sent to topic '{}': {}", topic, message);
        } catch (Exception e) {
            logger.error("Failed to send message to topic '{}': {}", topic, e.getMessage(), e);
        }
    }
}

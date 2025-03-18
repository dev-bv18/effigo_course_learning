package com.example.kafka1.kafkaProj.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.errors.TimeoutException;
import org.apache.kafka.common.errors.RetriableException;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

@EnableKafka
@Service
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final int MAX_RETRIES = 3;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // Scheduled task to move messages from fallback topic to main topic every 5 seconds
    @Scheduled(fixedRate = 5000)  // Run every 5 seconds
    public void retryFallbackMessages() {
        logger.info("Checking fallback topic for messages to retry...");
    }

    // Consumer listening on fallback topic to consume messages and resend them to the main topic
    @KafkaListener(topics = "my-fallback-topic", groupId = "fallback-consumer-group")
    public void consumeFallbackMessage(ConsumerRecord<String, String> record) {
        String message = record.value();
        logger.info("Consuming message from fallback topic: {}", message);

        // Attempt to resend the message to the main topic
        sendMessageWithRetry("my-topic", message, 0);
    }

    // Retry sending message to the main topic if it failed
    public void sendMessageWithRetry(String topic, String message, int attempt) {
        if (attempt >= MAX_RETRIES) {
            logger.error("Failed after {} attempts. Message not moved to main topic.", MAX_RETRIES);
            return;
        }

        // Send the message asynchronously using CompletableFuture
        CompletableFuture<SendResult<String, String>> future = CompletableFuture.supplyAsync(() -> {
            try {
                // Execute the Kafka send operation and block until completion
                return kafkaTemplate.send(topic, message).get();  // Using .get() to block and wait for result synchronously
            } catch (Exception ex) {
                throw new RuntimeException(ex);  // Rethrow exception to be handled in whenComplete
            }
        });

        // Handle result and failure with whenComplete
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("Message successfully sent to '{}': {}", topic, message);
            } else {
                // Check for retriable exceptions and retry
                if (ex instanceof TimeoutException || ex instanceof RetriableException) {
                    logger.warn("Retriable error, retrying message for '{}': {}", topic, message);
                    sendMessageWithRetry(topic, message, attempt + 1);
                } else {
                    logger.error("Non-retriable error, message failed to send to '{}': {}", topic, message, ex.getMessage());
                    sendToFallback(message);
                }
            }
        });
    }

    // Send to fallback topic if message cannot be delivered to main topic
    private void sendToFallback(String message) {
        kafkaTemplate.send("my-fallback-topic", message)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        logger.info("Message moved to fallback topic: {}", message);
                    } else {
                        logger.error("Failed to send to fallback topic: {}", ex.getMessage());
                    }
                });
    }
}

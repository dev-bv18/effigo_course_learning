package com.example.kafka1.kafkaProj.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.TimeoutException;
import org.apache.kafka.common.errors.RetriableException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.*;

@Service
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final int MAX_RETRIES = 3;
    private static final String TRANSACTION_TOPIC = "my-topic";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Queue to store failed messages
    private final BlockingQueue<String> failedQueue = new LinkedBlockingQueue<>();

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessageWithRetry(String topic, String message, int attempt) {
        if (attempt >= MAX_RETRIES) {
            logger.error("Max retries reached. Adding message to failed queue: {}", message);
            failedQueue.offer(message);  // Store in queue
            return;
        }

        CompletableFuture<SendResult<String, String>> future = CompletableFuture.supplyAsync(() -> {
            try {
                return kafkaTemplate.send(topic, message).get();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("Message successfully sent to '{}': {}", topic, message);
            } else {
                if (ex instanceof TimeoutException || ex instanceof RetriableException) {
                    logger.warn("Retriable error, retrying message for '{}': {}", topic, message);
                    sendMessageWithRetry(topic, message, attempt + 1);
                } else {
                    logger.error("Non-retriable error, adding message to failed queue: {}", message);
                    failedQueue.offer(message);
                }
            }
        });
    }

    @Scheduled(fixedRate = 10000)
    public void retryFailedMessages() {
        if (failedQueue.isEmpty()) {
            return;
        }

        logger.info("Retrying failed messages...");
        int size = failedQueue.size();
        for (int i = 0; i < size; i++) {
            String message = failedQueue.poll();  // Get message from queue
            if (message != null) {
                sendMessageWithRetry(TRANSACTION_TOPIC, message, 0);
            }
        }
    }
}

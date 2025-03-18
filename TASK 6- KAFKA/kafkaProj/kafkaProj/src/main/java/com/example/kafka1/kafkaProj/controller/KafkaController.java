package com.example.kafka1.kafkaProj.controller;

import com.example.kafka1.kafkaProj.entity.Item;
import com.example.kafka1.kafkaProj.service.ItemService;
import com.example.kafka1.kafkaProj.service.KafkaProducerService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/send")
public class KafkaController {

    private static final Logger logger = LoggerFactory.getLogger(KafkaController.class);

    private final KafkaProducerService producerService;
    private final ItemService itemService;
    private final ObjectMapper objectMapper;

    @Autowired
    public KafkaController(KafkaProducerService producerService, ItemService itemService, ObjectMapper objectMapper) {
        this.producerService = producerService;
        this.itemService = itemService;
        this.objectMapper = objectMapper;
    }

    // POST endpoint to send messages to Kafka
    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestParam String topic) {

        List<Item> items = itemService.getAllItems();

        if (items.isEmpty()) {
            logger.warn("No items available in the database.");
            return ResponseEntity.badRequest().body("No items available in the database.");
        }

        for (Item item : items) {
            try {
                String message = objectMapper.writeValueAsString(item);
                producerService.sendMessageWithRetry(topic, message, 0);  // Calling the correct method here
            } catch (Exception e) {
                logger.error("Failed to send message due to serialization error: {}", e.getMessage(), e);
                return ResponseEntity.status(500).body("Failed to send message due to serialization error.");
            }
        }

        logger.info("Messages sent asynchronously.");
        return ResponseEntity.ok("Messages sent to Kafka asynchronously!");
    }
}

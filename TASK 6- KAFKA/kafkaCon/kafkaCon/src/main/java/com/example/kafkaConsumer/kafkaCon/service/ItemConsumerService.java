package com.example.kafkaConsumer.kafkaCon.service;

import com.example.kafkaConsumer.kafkaCon.entity.Item;
import com.example.kafkaConsumer.kafkaCon.repository.ItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(ItemConsumerService.class);

    private final ItemRepository itemConsumerRepository;
    private final ObjectMapper objectMapper;

    public ItemConsumerService(ItemRepository itemRepository, ObjectMapper objectMapper) {
        this.itemConsumerRepository = itemRepository;
        this.objectMapper = objectMapper;
    }
    @KafkaListener(topics = "my-topic", groupId = "consumer-group-1")
    @Transactional
    public void consumeMessage(String message) {
        try {
            logger.info("Received message: {}", message);

            Item item = objectMapper.readValue(message, Item.class);
            itemConsumerRepository.save(item);

            logger.info("Message consumed and saved with item ID: {}", item.getItemId());

        } catch (Exception e) {
            logger.error("Failed to process Kafka message: {}", message, e);
        }
    }
}

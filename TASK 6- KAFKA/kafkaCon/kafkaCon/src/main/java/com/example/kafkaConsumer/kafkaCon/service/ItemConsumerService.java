package com.example.kafkaConsumer.kafkaCon.service;


import com.example.kafkaConsumer.kafkaCon.entity.Item;
import com.example.kafkaConsumer.kafkaCon.repository.ItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemConsumerService {

    private final ItemRepository itemConsumerRepository;
    private final ObjectMapper objectMapper;

    public ItemConsumerService(ItemRepository itemRepository, ObjectMapper objectMapper) {
        this.itemConsumerRepository = itemRepository;
        this.objectMapper = objectMapper;
    }

    // Kafka Listener to consume messages from the topic
    @KafkaListener(topics = "my-topic", groupId = "consumer-group-1")
    @Transactional
    public void consumeMessage(String message) {
        try {
            // Deserialize JSON message to ItemConsumer object
            Item item = objectMapper.readValue(message, Item.class);

            // Save the consumed item to the database
            itemConsumerRepository.save(item);

            // Log success
            System.out.println("✅ Message consumed and saved: " + item.getItemId());

        } catch (Exception e) {
            // Handle exceptions (e.g., deserialization errors)
            System.err.println("❌ Failed to process Kafka message: " + e.getMessage());
        }
    }
}


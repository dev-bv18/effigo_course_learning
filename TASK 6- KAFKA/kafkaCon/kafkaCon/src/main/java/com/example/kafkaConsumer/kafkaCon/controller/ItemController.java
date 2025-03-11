package com.example.kafkaConsumer.kafkaCon.controller;

import com.example.kafkaConsumer.kafkaCon.entity.Item;
import com.example.kafkaConsumer.kafkaCon.repository.ItemRepository;
import com.example.kafkaConsumer.kafkaCon.service.ItemConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/consumer")
public class ItemController {
    private final ItemConsumerService itemConsumerService;
    private final ItemRepository itemRepository;

    @Autowired
    public ItemController(ItemConsumerService itemConsumerService,ItemRepository itemRepository){
        this.itemConsumerService=itemConsumerService;
        this.itemRepository=itemRepository;
    }
    @GetMapping("/items")
    public ResponseEntity<List<Item>> getAllConsumedItems(){
        List<Item> items=itemRepository.findAll();
        return ResponseEntity.ok(items);
    }
    @GetMapping("/item/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable("id") String itemId) {
        return itemRepository.findById(itemId)
                .map(item -> ResponseEntity.ok(item))
                .orElse(ResponseEntity.notFound().build());
    }

    // Health check endpoint (optional)
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Consumer service is running!");
    }
}

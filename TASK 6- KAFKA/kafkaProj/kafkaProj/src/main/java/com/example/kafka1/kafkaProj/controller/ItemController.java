package com.example.kafka1.kafkaProj.controller;

import com.example.kafka1.kafkaProj.entity.Item;
import com.example.kafka1.kafkaProj.service.ItemService;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Item> getItemById(@PathVariable String itemId) {
        Optional<Item> item = itemService.getItemById(itemId);
        return item.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

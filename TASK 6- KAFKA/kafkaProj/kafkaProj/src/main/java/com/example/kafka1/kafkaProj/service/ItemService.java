package com.example.kafka1.kafkaProj.service;

import com.example.kafka1.kafkaProj.entity.Item;
import com.example.kafka1.kafkaProj.repository.ItemRepository;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Optional<Item> getItemById(String itemId) {
        return itemRepository.findById(itemId);
    }

}

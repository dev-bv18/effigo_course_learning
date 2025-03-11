package com.example.kafkaConsumer.kafkaCon.repository;

import com.example.kafkaConsumer.kafkaCon.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item,String> {
}

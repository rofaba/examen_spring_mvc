package org.example.examenspringmvc.repository;

import org.example.examenspringmvc.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ItemRepository extends MongoRepository<Item, String> {

    List<Item> findAll();
    List<Item> findByCountLessThan(int count);


}